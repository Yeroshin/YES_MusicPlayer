package com.yes.trackdialogfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.UiEvent

import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.usecase.GetRootMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.ShowChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.mapper.MenuMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TrackDialogViewModel(
    private val getRootMenuUseCase: GetRootMenuUseCase,
    private val showChildMenuUseCase: ShowChildMenuUseCase,
    private val menuMapper: MenuMapper
) : ViewModel() {

    private val _stateItemsMedia = MutableStateFlow<TrackDialogContract.TrackDialogState>(
        TrackDialogContract.TrackDialogState.Loading)
    val uiState: StateFlow<TrackDialogContract.TrackDialogState> = _stateItemsMedia

    private val _event: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            val result = getRootMenuUseCase(GetRootMenuUseCase.Params(Unit))
            when (result) {
                is Result.Success -> {
                    val item = menuMapper.map(
                        result.data,
                        ::setEvent
                    )
                    _stateItemsMedia.value = TrackDialogContract.TrackDialogState.Success(item)
                }
                is Result.Error -> {}
            }
        }
    }

    fun setEvent(event: UiEvent) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }


  /*  fun onEvent(event: TrackDialogEvent) {
        when (event) {
            is TrackDialogEvent.GetChildMenu -> onGetChildMenu(event.title, event.name)
        }
    }*/


    private fun onGetChildMenu(title: String, name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = showChildMenuUseCase(
                ShowChildMenuUseCase.Params(
                    title,
                    name
                )
            )
            when (result) {
                is Result.Success -> {
                    val item = menuMapper.map(
                        result.data,
                        ::setEvent
                    )
                    _stateItemsMedia.value = TrackDialogContract.TrackDialogState.Success(item)
                }
                is Result.Error -> {}
            }
        }

    }

    class Factory(
        private val getRootMenuUseCase: GetRootMenuUseCase,
        private val showChildMenuUseCase: ShowChildMenuUseCase,
        private val menuMapper: MenuMapper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackDialogViewModel(
                getRootMenuUseCase,
                showChildMenuUseCase,
                menuMapper
            ) as T
        }
    }
}





