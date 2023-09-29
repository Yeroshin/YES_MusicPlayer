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
import androidx.fragment.app.commit
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.yes.musicplayer.R
import com.yes.musicplayer.YESApplication
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.player.presentation.ui.PlayerFragment
import com.yes.playlistfeature.presentation.PlaylistFragment
import com.yes.trackdialogfeature.presentation.ui.TrackDialog


class MainActivity :
    AppCompatActivity(),
    PlaylistFragment.MediaChooserManager {
    interface DependencyResolver {
        fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent
    }

    private val myApplication: YESApplication by lazy {
        application as YESApplication
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

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root


        setContentView(view)
        supportFragmentManager.fragmentFactory = fragmentFactory
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

        val playerFragment = supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            PlayerFragment::class.java.name
        )


        supportFragmentManager.beginTransaction()
            .add(R.id.player_controls, playerFragment)
            .commit()
       /* supportFragmentManager.commit {

            replace(R.id.player_controls, playerFragment)
        }*/
    }

    class MainActivityFragmentFactory(
        private val trackDialogDependency: TrackDialog.Dependency,

        ) : FragmentFactory() {
        override fun instantiate(
            classLoader: ClassLoader,
            className: String
        ): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                //TrackDialog::class.java -> TrackDialog()
                TrackDialog::class.java -> TrackDialog(trackDialogDependency)
                PlayerFragment::class.java -> PlayerFragment()
                //  PlaylistFragment::class.java -> PlaylistFragment()
                PlaylistFragment::class.java -> PlaylistFragment()

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
}

