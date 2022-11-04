package com.yes.musicplayer


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayoutMediator
import com.yes.musicplayer.databinding.ActivityMainBinding
import com.yes.musicplayer.di.components.DaggerActivityComponent
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.musicplayer.presentation.FragmentAdapter
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var fragmentAdapter: FragmentAdapter
    @Inject
    lateinit var fragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerActivityComponent.builder()
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

