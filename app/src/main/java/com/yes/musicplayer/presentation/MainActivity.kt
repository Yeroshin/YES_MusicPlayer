package com.yes.musicplayer.presentation


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.net.Uri
import android.os.Build

import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.google.android.play.core.ktx.startUpdateFlowForResult
import com.yes.alarmclockfeature.presentation.ui.AlarmsScreen
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.musicplayer.equalizer.presentation.ui.EqualizerScreen
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistfeature.presentation.ui.PlaylistScreen
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


class MainActivity :
    AppCompatActivity(),
    PlaylistScreen.MediaChooserManager,
    PlaylistScreen.PlaylistManager {
    interface DependencyResolver {
        fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent
    }
    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType=AppUpdateType.IMMEDIATE
    private val updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {

        }
    }
    private val installStateUpdatedListener=InstallStateUpdatedListener{state->
        if(state.installStatus()==InstallStatus.DOWNLOADED){
             Toast.makeText(
                 applicationContext,
                 "Download successful. Restarting app in 5 seconds",
                 Toast.LENGTH_LONG
             ).show()
            lifecycleScope.launch {
                delay(5.seconds)
                appUpdateManager.completeUpdate()
            }
        }
    }
    private fun checkForUpdates(){
        appUpdateManager.appUpdateInfo.addOnSuccessListener {info->
            val isUpdateAvailable=info.updateAvailability()==UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed=when(updateType){
                AppUpdateType.FLEXIBLE->info.isFlexibleUpdateAllowed
                AppUpdateType.IMMEDIATE->info.isImmediateUpdateAllowed
                else->false
            }
            if(isUpdateAvailable&&isUpdateAllowed){
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateLauncher,
                    AppUpdateOptions.newBuilder(updateType).build()
                )
            }
        }
    }

    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as ActivityMainBinding
    }
    private val dependencyResolver by lazy {
        application as DependencyResolver
    }
    private val mainActivityComponent by lazy {
        dependencyResolver.getMainActivityComponent(this)
    }

    /*  private val fragmentAdapter: FragmentStateAdapter by lazy {
          mainActivityComponent.getFragmentAdapter()
      }

      private val fragmentFactory: FragmentFactory by lazy {
          mainActivityComponent.getFragmentFactory()
      }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appUpdateManager=AppUpdateManagerFactory.create(applicationContext)
        if(updateType==AppUpdateType.FLEXIBLE){
            appUpdateManager.registerListener(installStateUpdatedListener)
        }
        checkForUpdates()
        checkPermissions()
        checkBatteryOptimizations()
       // createNotificationChannel(this)

    }
    private fun checkBatteryOptimizations(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent();
            val packageName = packageName;
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:$packageName"))
                startActivity(intent)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if(updateType==AppUpdateType.IMMEDIATE){
            appUpdateManager.appUpdateInfo.addOnSuccessListener{info->
                if(info.updateAvailability()==UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateLauncher,
                        AppUpdateOptions.newBuilder(updateType).build()
                    )
                }
            }
        }

    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        } else {
            packageManager.getPackageInfo(
                packageName, PackageManager.PackageInfoFlags.of(
                    PackageManager.GET_PERMISSIONS.toLong()
                )
            )
        }?.requestedPermissions?.filter { permission ->
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageManager.getPermissionInfo(
                        permission,
                        PackageManager.GET_META_DATA
                    ).protection == PermissionInfo.PROTECTION_DANGEROUS
                } else {
                    packageManager.getPermissionInfo(
                        permission,
                        PackageManager.GET_META_DATA
                    ).protectionLevel == PermissionInfo.PROTECTION_DANGEROUS
                }
            } catch (e: Exception) {
                return@filter false
            }
        }?.filter { dangerousPermission ->
            ContextCompat.checkSelfPermission(
                this,
                dangerousPermission
            ) == PackageManager.PERMISSION_DENIED
        }?.let {
            if (it.isNotEmpty()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, it[0])) {
                    Toast.makeText(this, "Grant all needed permissions!!!", Toast.LENGTH_LONG)
                        .show()
                    ActivityCompat.requestPermissions((this as Activity), it.toTypedArray(), 1)
                } else {
                    ActivityCompat.requestPermissions((this as Activity), it.toTypedArray(), 1)
                }
            } else {
                setFragments()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissions.filterIndexed { index, _ ->
            grantResults[index] == PackageManager.PERMISSION_DENIED
        }.let {
            if (it.isNotEmpty()) {
                Toast.makeText(
                    this,
                    "Player is unavailable because the feature requires a permission that you has denied.",
                    Toast.LENGTH_LONG
                ).show()
                // checkPermissions()
            } else {
                setFragments()
            }

            // checkPermissions()
        }
        ////////////////
        /*  val deniedResults=permissions.filterIndexed { index, _ ->
              grantResults[index] == PackageManager.PERMISSION_DENIED
          }
          ActivityCompat.requestPermissions(this, deniedResults.toTypedArray(), 0)*/
        ////////////////


        /*   var granted = 0
           val notGranted= mutableListOf<String>()
           for (i in grantResults.indices) {
               if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                   granted++
               }else{
                 notGranted.add(permissions[i])
               }
           }
           if (granted != grantResults.size) {
               Toast.makeText(this, "error Permissions", Toast.LENGTH_LONG).show()
               ActivityCompat.requestPermissions((this as Activity), notGranted.toTypedArray(), 1)

             //  finish()//show explain why you need permission
           } else {
               setFragments()
           }*/
    }

    private fun setFragments() {

        binding = ActivityMainBinding.inflate(layoutInflater)
        // supportFragmentManager.fragmentFactory = fragmentFactory
        val view = binding.root
        setContentView(view)

        val fragmentsList = listOf(
            PlaylistScreen::class.java,
            EqualizerScreen::class.java,
            AlarmsScreen::class.java
        )
        binder.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                binder.viewPager.isUserInputEnabled = false
            }
        })
        binder.viewPager.adapter =
            UniversalFragmentAdapter(
                this,
                fragmentsList,
                supportFragmentManager.fragmentFactory
            )
        TabLayoutMediator(binder.tabs, binder.viewPager) { tab, position ->
            run {
                when (position) {
                    0 -> tab.text = getString(com.yes.coreui.R.string.playList)
                    1 -> tab.text = getString(com.yes.coreui.R.string.equalizer)
                    2 -> tab.text = getString(com.yes.coreui.R.string.alarm)
                }
            }
        }.attach()

        /*  val playerFragment = supportFragmentManager.fragmentFactory.instantiate(
              classLoader,
              PlayerFragment::class.java.name
          )*/
        /*  supportFragmentManager.commit {
              setReorderingAllowed(true)
              add<PlayerFragment>(R.id.player_controls)
          }*/

    }

    override fun onDestroy() {
        super.onDestroy()
        if(updateType==AppUpdateType.FLEXIBLE){
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        }
    }

   /* private fun createNotificationChannel(context: Context){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name:CharSequence="music"
            val description="music alarm"
            val importance=NotificationManager.IMPORTANCE_HIGH
            val channel=NotificationChannel("yes",name, importance)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.description=description
            val notificationManager=
                context.getSystemService( NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
    }*/

   /* class MainActivityFragmentFactory(

    ) : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                PlayListDialog::class.java -> PlayListDialog()
                TrackDialog::class.java -> TrackDialog()
                PlaylistScreen::class.java -> PlaylistScreen()
                PlayerFragment::class.java -> PlayerFragment()
                else -> super.instantiate(classLoader, className)
            }
        }
    }*/

    override fun showMediaDialog() {
        TrackDialog().show(supportFragmentManager, null)
    }

    override fun showPlaylistDialog() {
        (supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            PlayListDialog::class.java.name
        ) as DialogFragment).show(supportFragmentManager, null)
    }
}

