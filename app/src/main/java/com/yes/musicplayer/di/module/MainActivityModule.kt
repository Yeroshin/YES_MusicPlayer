package com.yes.musicplayer.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yes.core.util.EspressoIdlingResource
import com.yes.musicplayer.presentation.MainActivity
import com.yes.musicplayer.presentation.UniversalFragmentAdapter
import com.yes.player.presentation.PlayerFragment
import com.yes.playlistfeature.presentation.ui.Playlist
import com.yes.trackdialogfeature.presentation.ui.TrackDialog

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import androidx.datastore.preferences.core.Preferences
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"
@Module
internal class MainActivityModule(
    private val activity: FragmentActivity
) {

    @Provides
    fun provideFragment(
        fragmentFactory: FragmentFactory
    ): Fragment {
        return fragmentFactory.instantiate(
            ClassLoader.getSystemClassLoader(),
            PlayerFragment::class.java.name
        )
       // return PlayerFragment()
    }
    @Provides
    @Singleton
    fun provideDataStore( context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
    @Provides
    fun provideMainActivityFragmentFactory(
        trackDialogDependency: TrackDialog.Dependency,
        playListDialogDependency: PlayListDialog.Dependency,
        playlistDependency:Playlist.Dependency
    ): FragmentFactory {
        return MainActivity.MainActivityFragmentFactory(
            trackDialogDependency,
            playListDialogDependency,
            playlistDependency
        )
    }
    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
    @Provides
    fun providesEspressoIdlingResource(): EspressoIdlingResource? {
        return null
    }
    @Provides
    fun provideFragmentAdapter(
        fragmentFactory: FragmentFactory
    ): FragmentStateAdapter {


        val fragmentsList = listOf( Playlist::class.java)

        return UniversalFragmentAdapter(activity, fragmentsList, fragmentFactory)
    }
}