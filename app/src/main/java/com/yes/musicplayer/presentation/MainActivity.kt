package com.yes.musicplayer.presentation


import android.app.Activity
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Build

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.yes.alarmclockfeature.presentation.ui.AlarmsScreen
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.player.presentation.ui.PlayerFragment
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistfeature.presentation.ui.PlaylistScreen
import com.yes.trackdialogfeature.presentation.ui.TrackDialog


class MainActivity :
    AppCompatActivity(),
    PlaylistScreen.MediaChooserManager,
    PlaylistScreen.PlaylistManager {
    interface DependencyResolver {
        fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent
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
        checkPermissions()


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
                    "Player is unavailable because the feature requires a permission that the you has denied.",
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
            AlarmsScreen::class.java
        )
        binder.viewPager.adapter = UniversalFragmentAdapter(this, fragmentsList, supportFragmentManager.fragmentFactory)
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

    class MainActivityFragmentFactory(

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

