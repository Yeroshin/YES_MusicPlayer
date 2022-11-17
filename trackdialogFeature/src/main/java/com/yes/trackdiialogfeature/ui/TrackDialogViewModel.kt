package com.yes.trackdiialogfeature.ui

import androidx.lifecycle.ViewModel
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu
import com.yes.trackdiialogfeature.domain.MenuInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrackDialogViewModel(private val menuInteractor: MenuInteractor):ViewModel() {
    private val _stateItemsMedia = MutableStateFlow(menuInteractor.getMenu())
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
    fun getMenuItemContent(menu:Menu){
        menuInteractor.getMenuContent(menu)
    }

}