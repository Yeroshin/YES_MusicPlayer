package com.yes.trackdiialogfeature.data.repository.entity

import com.yes.trackdiialogfeature.domain.entity.Menu

class MenuParam(
    val type:String,
    val where: String?,
    val what: Array<String>,
    val name:String,
    val parentMenu: Menu?
    )