package com.yes.trackdiialogfeature.presentation.entity

import com.yes.trackdiialogfeature.domain.entity.Menu

data class MenuUi(
    val name: String,
    var title: String,
    var type: String,
    var parent: Menu,
    var selected: Int,
    val onClick: () -> Unit
)