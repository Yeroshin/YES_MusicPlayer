package com.yes.trackdiialogfeature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yes.trackdiialogfeature.domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrackDialogViewModel(private val showChildMenu: UseCase<Menu,Menu>):ViewModel() {

    private val _stateItemsMedia = MutableStateFlow(Menu("",null))
    val uiState:StateFlow<Menu> =_stateItemsMedia
   /* fun init(){
        val items = arrayListOf<MediaItem>()

        for (i in 0..10) {
            val item = MediaItem()
            item.title = "message"
            items.add(item)
        }

        _stateItemsMedia.value=menuInteractor.getMenu()
    }*/
    fun getMenuItemContent(menu:Menu?){

        showChildMenu.invoke(menu,viewModelScope){
            _stateItemsMedia.value=it
        }
    }

}