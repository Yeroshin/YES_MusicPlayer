package com.yes.musicplayer.presentation


import android.Manifest
import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.player.presentation.ui.PlayerFragment
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistfeature.presentation.ui.Playlist
import com.yes.trackdialogfeature.presentation.ui.TrackDialog


class MainActivity :
    AppCompatActivity(),
    Playlist.MediaChooserManager,
    Playlist.PlaylistManager {
    interface DependencyResolver {
        fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent
    }

    private val myApplication: DependencyResolver by lazy {
        application as DependencyResolver
    }
    private lateinit var binding: ActivityMainBinding
    private val mainActivityComponent: MainActivityComponent by lazy {
        myApplication.getMainActivityComponent(this)
    }

    private val fragmentAdapter: FragmentStateAdapter by lazy {
        mainActivityComponent.getFragmentAdapter()
    }

    /*private val playerFragment: Fragment by lazy {
        mainActivityComponent.getPlayerFragment()
    }*/
    private val fragmentFactory: FragmentFactory by lazy {
        mainActivityComponent.getFragmentFactory()
    }

    /*  private val trackDialogDependency: TrackDialog.Dependency by lazy {
          mainActivityComponent.getTrackDialogDependency()
      }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkPermissions()

    }

    private fun checkPermissions() {

        /////////////////////
        var info: PackageInfo? = null
        try {
            info = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val perm = info!!.requestedPermissions
        val permissionsDenied: ArrayList<String?> = ArrayList()
        val list: MutableList<String> = ArrayList()

        list.add(Manifest.permission.RECORD_AUDIO)
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissions = list.toTypedArray()
        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permissions[i]
                ) == PackageManager.PERMISSION_DENIED
            ) {
                permissionsDenied.add(permissions[i])
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (this as Activity),
                        permissions[i]
                    )
                ) {

                }
            }
        }
        if (permissionsDenied.size > 0) {
            val permissionsArray = permissionsDenied.toTypedArray()
            ActivityCompat.requestPermissions((this as Activity), permissionsArray, 1)
        } else {
            setFragments()
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var granted = 0
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted++
            }
        }
        if (granted != grantResults.size) {
            finish()
        } else {
            setFragments() //show explain why you need permission
        }
    }

    private fun setFragments() {
        binding.viewPager.adapter = fragmentAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
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
        private val trackDialogDependency: TrackDialog.Dependency,
        private val playListDialogDependency: PlayListDialog.Dependency,
        private val playlistDependency:Playlist.Dependency,
        private val playerDependency:PlayerFragment.Dependency
        ) : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                PlayListDialog::class.java -> PlayListDialog(playListDialogDependency)
                TrackDialog::class.java -> TrackDialog(trackDialogDependency)
                PlayerFragment::class.java -> PlayerFragment(playerDependency)
                Playlist::class.java -> Playlist(playlistDependency)

                else -> super.instantiate(classLoader, className)
            }
        }
    }


    override fun showMediaDialog() {
        (supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            TrackDialog::class.java.name
        ) as DialogFragment).show(supportFragmentManager, null)

    }

    override fun showPlaylistDialog() {
        (supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            PlayListDialog::class.java.name
        ) as DialogFragment).show(supportFragmentManager, null)
    }
}

