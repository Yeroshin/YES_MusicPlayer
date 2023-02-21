package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.domain.entity.Menu

object DomainFixtures {


        fun getRootMenu() : Menu {
            val item1 = Menu("test body 1")
            val item2 = Menu("test body 2")
            val item3 = Menu("test body 3")
            val menu=Menu("categories")
            menu.children = arrayListOf(item1,item2,item3)
            return menu
        }


}