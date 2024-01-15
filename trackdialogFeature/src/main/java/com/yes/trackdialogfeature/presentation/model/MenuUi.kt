package com.yes.trackdialogfeature.presentation.model

import com.yes.trackdialogfeature.domain.usecase.CheckNetworkPathAvailableUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract


data class MenuUi(
    val title: String,
    val items: List<ItemUi>,
) {
    data class ItemUi(
        val id:Int=-1,
        val name:String="",
        val iconType:Int=0,
        var selected:Boolean?=null,
        val param:TrackDialogContract.Event=TrackDialogContract.Event.OnItemClicked(
            id,
            name,
        ),
        val onClick: ((TrackDialogContract.Event) -> Unit)={}
    )
    ////////////////

}