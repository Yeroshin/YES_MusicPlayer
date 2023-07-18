package com.yes.trackdialogfeature.presentation.model

import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract


data class MenuUi(
    val title: String,
    val items: List<ItemUi>
) {
    data class ItemUi(
        val id:Int,
        val name:String,
        val iconType:Int,
        var selected:Boolean?,
        val param:TrackDialogContract.Event,
        val onClick: ((TrackDialogContract.Event) -> Unit)
    )
}