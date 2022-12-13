package com.yes.trackdiialogfeature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yes.trackdiialogfeature.domain.MediaQuery
import com.yes.trackdiialogfeature.domain.entity.Menu
import com.yes.trackdiialogfeature.domain.usecase.GetRootMenu
import com.yes.trackdiialogfeature.domain.usecase.ShowChildMenu
import com.yes.trackdiialogfeature.domain.usecase.UseCase
import com.yes.trackdiialogfeature.domain.usecase.UseCaseWithParam
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrackDialogViewModel(
    private val showChildMenu: ShowChildMenu,
    private val getRootMenu: GetRootMenu
) : ViewModel() {

    private val _stateItemsMedia =
        MutableStateFlow<TrackDialogViewModelState>(TrackDialogViewModelState.Init)
    val uiState: StateFlow<TrackDialogViewModelState> = _stateItemsMedia

    /* fun init(){
         val items = arrayListOf<MediaItem>()

         for (i in 0..10) {
             val item = MediaItem()
             item.title = "message"
             items.add(item)
         }

         _stateItemsMedia.value=menuInteractor.getMenu()
     }*/
    fun trackDialogStateFlow() {

    }

    fun getMenu() {
        getRootMenu(viewModelScope) { result ->
            _stateItemsMedia.value = TrackDialogViewModelState.Success(result)
        }
    }

   /* fun getMenuItemContent(name: String) {

        showChildMenu(
            MediaQuery(name),
            viewModelScope
        ) { result ->
            //_stateItemsMedia.value = it
            _stateItemsMedia.value = TrackDialogViewModelState.Success(result)
        }

    }*/
}

sealed class TrackDialogViewModelState {
    object Init : TrackDialogViewModelState()
    data class IsLoading(val isLoading: Boolean) : TrackDialogViewModelState()
    data class Success(val menu: Menu) : TrackDialogViewModelState() {
        val title: String = ""

    }
}