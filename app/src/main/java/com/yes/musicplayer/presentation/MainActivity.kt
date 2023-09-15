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
import com.google.android.material.tabs.TabLayoutMediator
import com.yes.musicplayer.R
import com.yes.musicplayer.YESApplication
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.trackdialogfeature.presentation.ui.TrackDialog


class MainActivity : AppCompatActivity() {
    interface Main {
        fun getTrackDialogDependency(): TrackDialog.Dependency
        fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent
    }

    private lateinit var binding: ActivityMainBinding
    private val mainActivityComponent: MainActivityComponent by lazy {
        myApplication.getMainActivityComponent(this)
    }

    private val fragmentAdapter: FragmentAdapter by lazy {
        mainActivityComponent.getFragmentAdapter()
    }

    private val playerFragment: Fragment by lazy {
        mainActivityComponent.getPlayerFragment()
    }
    private val myApplication: YESApplication by lazy {
        application as YESApplication
    }
    private val trackDialogDependency: TrackDialog.Dependency by lazy {
        myApplication.getTrackDialogDependency()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = MainActivityFragmentFactory(trackDialogDependency)
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
            showTrackDialog()
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


        supportFragmentManager.commit {

            replace(R.id.player_controls, playerFragment)
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

    private fun showTrackDialog() {
        (supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            TrackDialog::class.java.name
        ) as DialogFragment).show(supportFragmentManager, null)
    }

    class MainActivityFragmentFactory(
        private val dependency: TrackDialog.Dependency
    ) : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                TrackDialog::class.java -> TrackDialog(dependency)
                else -> super.instantiate(classLoader, className)
            }
        }
    }
}

