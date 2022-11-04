package com.yes.musicplayer.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayoutMediator
import com.yes.musicplayer.R
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.DaggerMainActivityComponent

import com.yes.musicplayer.di.module.MainActivityModule
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
}

