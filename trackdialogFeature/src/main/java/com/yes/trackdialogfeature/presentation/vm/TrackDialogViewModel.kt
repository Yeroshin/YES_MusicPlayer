package com.yes.trackdialogfeature.presentation.vm

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.BaseViewModel

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TrackDialogViewModel(
    private val getChildMenuUseCase: GetChildMenuUseCase,
    private val menuUiDomainMapper: MenuUiDomainMapper
) : BaseViewModel<TrackDialogContract.Event,
        TrackDialogContract.State,
        TrackDialogContract.Effect>() {
    private val menuStack = LinkedList<MenuUi>()
    override fun createInitialState(): TrackDialogContract.State {
        return TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Idle
        )
    }

    override fun handleEvent(event: TrackDialogContract.Event) {
        when (event) {
            is TrackDialogContract.Event.OnItemClicked -> {
                getChildMenu(event.id, event.name)
            }
            is TrackDialogContract.Event.OnItemBackClicked -> {
                getParentMenu()
            }
        }
    }

    private fun getParentMenu() {

    }

    private fun getChildMenu(id: Int, name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    trackDialogState = TrackDialogContract.TrackDialogState.Loading
                )
            }
            val result = getChildMenuUseCase(
                GetChildMenuUseCase.Params(
                    id,
                    name
                )
            )
            when (result) {
                is DomainResult.Success -> setState {
                    val menuUi = menuUiDomainMapper.map(
                        result.data,
                        ::setEvent
                    )
                    if(!menuStack.isEmpty()){
                        menuStack?.let {
                            menuUi.items
                                .toMutableList()
                                .add(
                                    MenuUi.MediaItem(
                                        0,
                                        "..",
                                        0,
                                        TrackDialogContract.Event.OnItemBackClicked,
                                        ::setEvent
                                    )
                                )
                            menuStack.add(menuUi)
                        }
                    }


                    copy(
                        trackDialogState = TrackDialogContract.TrackDialogState.Success(menuUi)
                    )
                }
                is DomainResult.Error -> {
                    when (result.exception) {
                        is DomainResult.UnknownException -> {
                            setState {
                                copy(
                                    trackDialogState = TrackDialogContract.TrackDialogState.Idle
                                )
                            }
                            setEffect {
                                TrackDialogContract.Effect.UnknownException
                            }
                        }
                        is MenuException.Empty -> {
                            setState {
                                copy(
                                    trackDialogState = TrackDialogContract.TrackDialogState.Idle
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    class Factory(
        private val getChildMenuUseCase: GetChildMenuUseCase,
        private val menuUiDomainMapper: MenuUiDomainMapper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackDialogViewModel(
                getChildMenuUseCase,
                menuUiDomainMapper
            ) as T
        }
    }

    fun test(): Int {
        Log.i(ContentValues.TAG, "////test: ////")
        return 527
    }
}





