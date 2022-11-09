package com.yes.trackdiialogfeature.di.module

import com.yes.trackdiialogfeature.presentation.TrackDialogAdapter
import dagger.Module
import dagger.Provides

@Module
class TrackDialogModule {
    @Provides
    fun provideMediaDialogAdapter():TrackDialogAdapter{
        return TrackDialogAdapter()
    }
}