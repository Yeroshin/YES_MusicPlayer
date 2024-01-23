package com.yes.musicplayer.equalizer.di.module

import com.yes.musicplayer.equalizer.presentation.ui.EqualizerScreen
import com.yes.musicplayer.equalizer.presentation.vm.EqualizerViewModel
import dagger.Module
import dagger.Provides

@Module
class EqualizerModule {
    @Provides
    fun providesEqualizerViewModelFactory(

        ): EqualizerViewModel.Factory {
        return EqualizerViewModel.Factory(

            )
    }
    @Provides
    fun providesDependency(
        factory: EqualizerViewModel.Factory,
    ): EqualizerScreen.Dependency {
        return EqualizerScreen.Dependency(
            factory
        )
    }
}