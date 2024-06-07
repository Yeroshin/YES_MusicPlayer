package com.yes.musicplayer.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.ui.BaseViewModel
import com.yes.musicplayer.presentation.contract.MainActivityContract

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async


class ActivityViewModel(

):BaseViewModel<MainActivityContract.Event, MainActivityContract.State, MainActivityContract.Effect>() {
    //////////////////
    private val useCaseCoroutineScope: CoroutineScope=viewModelScope
    private fun withUseCaseScope(
        loadingUpdater: ((Boolean) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        block: (suspend () -> Unit)
    ): Deferred<Unit> {
        return useCaseCoroutineScope.async {
            loadingUpdater?.invoke(true)
            try {
                block()
            } catch (e: Exception) {
                onError?.invoke(e)
            } finally {
                loadingUpdater?.invoke(false)
                onComplete?.invoke()
            }
        }
    }
    //////////////////

    override fun createInitialState(): MainActivityContract.State {
        return MainActivityContract.State(
            MainActivityContract.ActivityState.Idle,
            null
        )
    }

    override fun handleEvent(event: MainActivityContract.Event) {
      /*  when(event){
            MainActivityContract.Event.OnGetTheme -> {}//getTheme()
        }*/
    }

    class Factory(

       ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ActivityViewModel(


                ) as T
        }
    }
}