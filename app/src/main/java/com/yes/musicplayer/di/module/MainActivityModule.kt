package com.yes.musicplayer.di.module

import dagger.Module


private const val USER_PREFERENCES = "user_preferences"
@Module
class MainActivityModule {

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