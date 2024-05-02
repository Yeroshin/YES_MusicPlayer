package com.yes.musicplayer.di.module

import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.di.module.IoDispatcher
import com.yes.core.presentation.ui.ActivityDependency
import com.yes.musicplayer.presentation.vm.ActivityViewModel
import com.yes.settings.domain.usecase.GetThemeUseCase
import com.yes.settings.domain.usecase.SetThemeUseCase

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher


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
   @Provides
   fun providesSettingsRepositoryImpl(
       settingsDataSource: SettingsDataSource
   ): SettingsRepositoryImpl {
       return SettingsRepositoryImpl(
           settingsDataSource
       )
   }

   @Provides
   fun providesGetThemeUseCase(
       @IoDispatcher dispatcher: CoroutineDispatcher,
       settingsRepositoryImpl: SettingsRepositoryImpl
   ): GetThemeUseCase {
       return GetThemeUseCase(
           dispatcher,
           settingsRepositoryImpl
       )
   }

   @Provides
   fun providesActivityViewModelFactory(

       ): ActivityViewModel.Factory {
       return ActivityViewModel.Factory(

           )
   }
   @Provides
   fun providesDependency(
       factory: ActivityViewModel.Factory,
       settingsRepositoryImpl: SettingsRepositoryImpl
   ): ActivityDependency{
       return ActivityDependency(
           factory,
           settingsRepositoryImpl
       )
   }
}