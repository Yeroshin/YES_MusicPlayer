package com.yes.musicplayer.presentation


import android.Manifest
import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

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
import com.yes.musicplayer.YESApplication
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.player.databinding.PlayerBinding
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
    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as ActivityMainBinding
    }
    private val dependencyResolver by lazy {
        application  as DependencyResolver
    }
    private val mainActivityComponent by lazy {
        dependencyResolver.getMainActivityComponent(this)
    }

    private val fragmentAdapter: FragmentStateAdapter by lazy {
        mainActivityComponent.getFragmentAdapter()
    }

    private val fragmentFactory: FragmentFactory by lazy {
        mainActivityComponent.getFragmentFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
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

        val permissionsDenied = mutableListOf<String>()
        val permissions = info?.requestedPermissions
            ?:return
        ////////////////////
        permissions.forEach {
            packageManager.getPermissionInfo(it, PackageManager.GET_META_DATA)
        }
        val dangerousPermissions= mutableListOf<String>()
        for(i in permissions.indices){
            val tmp= packageManager.getPermissionInfo(permissions[i], PackageManager.GET_META_DATA)
            Toast.makeText(this, "error Permissions", Toast.LENGTH_LONG).show()
        }

        //////////////////
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
            ActivityCompat.requestPermissions((this as Activity), permissionsDenied.toTypedArray(), 1)
        } else {
            setFragments()
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var granted = 0
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
        }
    }

    private fun setFragments() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.fragmentFactory = fragmentFactory
        val view = binding.root
        setContentView(view)
        binder .viewPager.adapter = fragmentAdapter
        TabLayoutMediator(binder .tabs,binder.viewPager) { tab, position ->
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
                Playlist::class.java -> Playlist()
                PlayerFragment::class.java -> PlayerFragment()
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

