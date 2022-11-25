package com.yes.trackdiialogfeature.data.repository.entity

import com.yes.trackdiialogfeature.domain.Menu

class MenuParam(
    val type:String,
    val where: String?,
    val what:ArrayList<String>?,
    val name:String,
    val parentMenu: Menu?
    )