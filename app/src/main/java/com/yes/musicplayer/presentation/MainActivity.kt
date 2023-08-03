package com.yes.musicplayer.presentation


import android.Manifest
import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayoutMediator
import com.yes.musicplayer.R
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.DaggerMainActivityComponent
import com.yes.musicplayer.di.module.MainActivityModule

import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var fragmentAdapter: FragmentAdapter

    @Inject
    lateinit var fragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(this))
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       // checkPermissions()
        ////////////////////
        val tmp= MediaDataStore(applicationContext)
       // tmp.getAudioItems(null,null)
        tmp.getAudioItems(MediaStore.Audio.Media.ARTIST,arrayOf("Gesaffelstein"))
////////////////////
    }

    fun checkPermissions() {

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
            setTabs()
        }

    }

    private fun setTabs() {
        binding.viewPager.adapter = fragmentAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            run {
                when (position) {
                    0 -> tab.setText(getString(com.yes.coreui.R.string.playList))
                    1 -> tab.setText(getString(com.yes.coreui.R.string.equalizer))
                    2 -> tab.setText(getString(com.yes.coreui.R.string.alarm))
                }
            }
        }.attach()


        supportFragmentManager.commit {

            replace(R.id.fragment_container, fragment)
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
            setTabs() //show explain why you need permission
        }
    }
}

