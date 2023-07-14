package com.yes.trackdialogfeature

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.DomainResultFactory
import com.yes.trackdialogfeature.domain.entity.Menu

open class MyTmp {
    fun mytest(): DomainResult<Menu>{
        //return DomainResult.Success(Menu("", listOf()))
        return DomainResultFactory.createSuccess(Menu("", listOf()))
    }
}