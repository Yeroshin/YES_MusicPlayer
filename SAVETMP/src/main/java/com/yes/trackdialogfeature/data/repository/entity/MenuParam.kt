package com.yes.trackdialogfeature.data.repository.entity

import com.yes.trackdialogfeature.domain.entity.Menu

class MenuParam(
    val type:String,
    val where: String?,
    val what: Array<String>,
    val name:String,
    val parentMenu: Menu?
    )