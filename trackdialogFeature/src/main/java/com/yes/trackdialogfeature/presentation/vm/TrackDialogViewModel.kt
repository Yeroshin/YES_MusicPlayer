package com.yes.trackdialogfeature.presentation.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.BaseViewModel

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase
import com.yes.trackdialogfeature.domain.usecase.UseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract.State
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract.Effect
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.model.MenuUi.ItemUi
import com.yes.core.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

open class TrackDialogViewModel(
    private val getMenuUseCase: UseCase<GetMenuUseCase.Params, Menu>,
    private val saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
    private val uiMapper: UiMapper,
    private val menuStack: ArrayDeque<MenuUi>,
    private val espressoIdlingResource: EspressoIdlingResource?
) : BaseViewModel<TrackDialogContract.Event, State, Effect>() {

    override fun createInitialState(): State {
        return State(
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

            is TrackDialogContract.Event.OnButtonOkClicked -> {
                saveItems(event.items)
            }

            is TrackDialogContract.Event.OnButtonCancelClicked -> {
                dismiss()
            }
        }
    }

    private fun dismiss() {
        setState {
            copy(
                trackDialogState = TrackDialogContract.TrackDialogState.Dismiss
            )
        }
    }

    private fun saveItems(items: List<ItemUi>) {
         setState {
             copy(
                 trackDialogState = TrackDialogContract.TrackDialogState.Idle
             )
         }
        espressoIdlingResource?.increment()
        viewModelScope.launch {
            val result = saveTracksToPlaylistUseCase(
                SaveTracksToPlaylistUseCase.Params(
                    //to do - filter back item
                    items.map {
                        uiMapper.map(it)
                    }
                )
            )
            espressoIdlingResource?.decrement()
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            trackDialogState = TrackDialogContract.TrackDialogState.Idle
                        )
                    }

                    dismiss()
                }

                else -> {

                    setEffect {
                        Effect.UnknownException
                    }
                    setState {
                        copy(
                            trackDialogState = TrackDialogContract.TrackDialogState.Idle
                        )
                    }
                }
            }


        }

    }

    private fun getParentMenu() {
        setState {
            copy(
                trackDialogState = TrackDialogContract.TrackDialogState.Loading
            )
        }
       // espressoIdlingResource?.increment()
        viewModelScope.launch {
            //delay(5000)


            menuStack.pollLast()
            menuStack.peekLast()?.let {

                setState {
                    copy(
                        trackDialogState = TrackDialogContract.TrackDialogState.Success(
                            it
                        )
                    )
                }
              //  espressoIdlingResource?.decrement()
            }?:run {
               // espressoIdlingResource?.decrement()
                setState {
                    copy(
                        trackDialogState = TrackDialogContract.TrackDialogState.Idle
                    )
                }
                setEffect {
                    Effect.UnknownException
                }
            }
            ///////////////////
          /*  menuStack.removeLast()
            if (menuStack.isEmpty()) {
                setState {
                    copy(
                        trackDialogState = TrackDialogContract.TrackDialogState.Idle
                    )
                }
                setEffect {
                    Effect.UnknownException
                }
            } else {
                setState {
                    copy(
                        trackDialogState = TrackDialogContract.TrackDialogState.Success(
                            //TO DOrefactor this
                            menuStack.peekLast()!!
                        )
                    )
                }
            }*/
            ////////////////////////
        }

    }

    private fun getChildMenu(id: Int?, name: String) {

        espressoIdlingResource?.increment()
        viewModelScope.launch {
            setState {
                copy(
                    trackDialogState = TrackDialogContract.TrackDialogState.Loading
                )
            }
            val params = id?.let {
                GetMenuUseCase.Params(
                    it,
                    name
                )
            }
            //  espressoIdlingResource?.increment()
            val result = getMenuUseCase(
                params
            )
             espressoIdlingResource?.decrement()
            when (result) {
                is DomainResult.Success -> setState {
                    var menuUi = uiMapper.map(
                        result.data,
                        ::setEvent
                    )
                    if (!menuStack.isEmpty()) {
                        val items = menuUi.items.toMutableList()
                        items.add(
                            0,
                            ItemUi(
                                -1,
                                "..",
                                0,
                                null,
                                TrackDialogContract.Event.OnItemBackClicked,
                                ::setEvent
                            )
                        )
                        val newMenuUI = menuUi.copy(items = items)
                        menuUi = newMenuUI
                    }
                    if (!menuStack.offer(menuUi)) {

                        setEffect {
                            Effect.UnknownException
                        }
                        copy(
                            trackDialogState = TrackDialogContract.TrackDialogState.Idle
                        )
                    } else {
                        /*  if (!EspressoIdlingResource.countingIdlingResource.isIdleNow) {
                              EspressoIdlingResource.decrement(); // Set app as idle.
                          }*/

                        copy(
                            trackDialogState = TrackDialogContract.TrackDialogState.Success(menuUi)
                        )
                    }

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
                                Effect.UnknownException
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
        private val getMenuUseCase: GetMenuUseCase,
        private val saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
        private val uiMapper: UiMapper,
        private val menuStack: ArrayDeque<MenuUi>,
        private val espressoIdlingResource: EspressoIdlingResource?
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TrackDialogViewModel(
                getMenuUseCase,
                saveTracksToPlaylistUseCase,
                uiMapper,
                menuStack,
                espressoIdlingResource,
            ) as T
        }
    }
}







