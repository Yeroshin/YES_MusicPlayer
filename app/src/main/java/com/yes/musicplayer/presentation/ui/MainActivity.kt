package com.yes.musicplayer.presentation.ui


import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Build

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.OnFailureListener
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
import com.yes.alarmclockfeature.presentation.ui.AlarmsScreen
import com.yes.core.presentation.model.Theme
import com.yes.core.presentation.ui.ActivityDependency
import com.yes.core.presentation.ui.BaseActivity
import com.yes.core.presentation.ui.BaseDependency
import com.yes.core.presentation.ui.UiState
import com.yes.coreui.R
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.musicplayer.equalizer.presentation.ui.EqualizerScreen
import com.yes.musicplayer.presentation.contract.MainActivityContract
import com.yes.player.presentation.contract.PlayerContract
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistfeature.presentation.ui.PlaylistScreen
import com.yes.settings.presentation.contract.SettingsContract
import com.yes.settings.presentation.ui.SettingsScreen
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds


class MainActivity :
    BaseActivity(),
    PlaylistScreen.MediaChooserManager,
    PlaylistScreen.PlaylistManager {
    interface DependencyResolver {
        fun resolveMainActivityDependency(): ActivityDependency
    }

    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.IMMEDIATE
    private val updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {

        }
    }
    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
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

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.registerListener(installStateUpdatedListener)
        }
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = when (updateType) {
                AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                else -> false
            }
            if (isUpdateAvailable && isUpdateAllowed) {
                try {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateLauncher,
                    AppUpdateOptions.newBuilder(updateType).build()
                )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }.addOnFailureListener {
            println("onFailure: onResume")
        }
    }

    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as ActivityMainBinding
    }

    override val dependency by lazy {
        (application as DependencyResolver)
            .resolveMainActivityDependency()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //////////////////////
      /*  CoroutineScope(Dispatchers.Main). launch {
            viewModel.uiState.collect {
                renderUiState(it)
                checkPermissions()
            }

            cancel() // Завершение жизненного цикла корутины
        }*/
        //////////////////////
        //  setTheme(com.yes.coreui.R.style.Theme_YESActivityDark)

        setTheme()
        super.onCreate(savedInstanceState)
        checkForUpdates()
           checkPermissions()
        setFragments()
    }
    private fun setTheme(){
        val tmp = theme
        runBlocking {
            when (dependency.settingsRepositoryImpl.getTheme()) {
                Theme.DarkTheme -> {
                    setTheme(R.style.Theme_YESActivityDark)

                }

                Theme.LightTheme -> {
                    setTheme(R.style.Theme_YESActivityLight)
                }
            }
        }
        val tmp1 = theme


    }

    override fun renderUiState(state: UiState) {
        state as MainActivityContract.State
        when (state.state) {
            is MainActivityContract.ActivityState.Success -> {
                render(state)
            }

            is MainActivityContract.ActivityState.Idle -> {}
        }
    }

    private fun render(state: MainActivityContract.State) {
       /* state.theme?.let {
            when (it) {
                Theme.DarkTheme -> {
                    setTheme(R.style.Theme_YESActivityDark)
                }

                Theme.LightTheme -> {
                    setTheme(R.style.Theme_YESActivityLight)
                }
            }
            checkPermissions()

        }*/
    }

    override fun showEffect() {

    }

    override fun setUpView() {
       /* viewModel.setEvent(
            MainActivityContract.Event.OnGetTheme {
              //  checkPermissions()
            }
        )*/

    }


    override fun onResume() {
        super.onResume()
        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
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
              //  setFragments()//TODO do something wit this
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

        }
    }

    private fun setFragments() {

        binding = ActivityMainBinding.inflate(layoutInflater)
        // supportFragmentManager.fragmentFactory = fragmentFactory
        val view = binding.root
        setContentView(view)

        val fragmentsList = listOf(
            PlaylistScreen::class.java,
            EqualizerScreen::class.java,
            AlarmsScreen::class.java,
            SettingsScreen::class.java
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
                    3 -> tab.text = getString(com.yes.coreui.R.string.settings)
                }
            }
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        }
    }

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

