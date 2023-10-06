package com.yes.playlistdialogfeature.presentation.model

    data class ItemUi (
        val id:Long,
        val name:String,
        val current:Boolean,
        var selected:Boolean=false,
    )

