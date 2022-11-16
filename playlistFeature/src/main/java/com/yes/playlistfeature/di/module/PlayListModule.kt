package com.yes.playlistfeature.di.module
import androidx.fragment.app.DialogFragment
import com.yes.trackdiialogfeature.ui.TrackDialog
import dagger.Module
import dagger.Provides
@Module
class PlayListModule {
    @Provides
    fun provideTrackDialog(): DialogFragment {
        return TrackDialog()
    }
}