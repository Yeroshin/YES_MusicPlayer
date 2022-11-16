package com.yes.trackdiialogfeature.ui

import androidx.lifecycle.ViewModel
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.MenuInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrackDialogViewModel(private val menuInteractor: MenuInteractor):ViewModel() {
    private val _stateItemsMedia = MutableStateFlow(arrayListOf<MediaItem>())
    val uiState:StateFlow<ArrayList<MediaItem>> =_stateItemsMedia
    fun init(){
        val items = arrayListOf<MediaItem>()
        for (i in 0..10) {
            val item = MediaItem()
            item.title = "message"
            items.add(item)
        }

        _stateItemsMedia.value=menuInteractor.getCategories()
    }
    fun selectItem(){

    }

}