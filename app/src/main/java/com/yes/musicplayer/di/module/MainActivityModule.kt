package com.yes.musicplayer.di.module

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yes.alarmclockfeature.presentation.ui.AlarmsScreen
import com.yes.musicplayer.presentation.UniversalFragmentAdapter
import com.yes.playlistfeature.presentation.ui.PlaylistScreen

import dagger.Module
import dagger.Provides


private const val USER_PREFERENCES = "user_preferences"
@Module
internal class MainActivityModule(
    private val activity: FragmentActivity
) {

  /*  @Provides
    fun provideFragmentActivity(
        activity: FragmentActivity
    ): FragmentActivity {
        return activity
    }*/
  /*  @Provides
    fun provideFragment(
        fragmentFactory: FragmentFactory
    ): Fragment {
        return fragmentFactory.instantiate(
            ClassLoader.getSystemClassLoader(),
            PlayerControls::class.java.name
        )
       // return PlayerFragment()
    }*/
    
   /* @Provides
    fun provideMainActivityFragmentFactory(
       activity: FragmentActivity
    ): FragmentFactory {
        return activity.supportFragmentManager.fragmentFactory
       /* return MainActivity.MainActivityFragmentFactory(
           /* trackDialogDependency,
            playListDialogDependency,
            playlistDependency,*/
          //  playerDependency
        )*/
    }*/



   /* @Provides
    fun provideFragmentAdapter(
        fragmentFactory: FragmentFactory
    ): FragmentStateAdapter {
        val fragmentsList = listOf(
            PlaylistScreen::class.java,
            AlarmsScreen::class.java
        )
        return UniversalFragmentAdapter(activity, fragmentsList, fragmentFactory)
    }*/

}