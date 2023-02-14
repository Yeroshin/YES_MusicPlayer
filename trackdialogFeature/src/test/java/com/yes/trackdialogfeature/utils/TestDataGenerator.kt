package com.yes.trackdialogfeature.utils

import com.yes.trackdialogfeature.domain.entity.Menu

class TestDataGenerator {

    companion object {
        fun generateRootMenu() : Menu {
            val item1 = Menu("test body 1")
            val item2 = Menu("test body 2")
            val item3 = Menu("test body 3")
            val menu=Menu("categories")
            menu.children = arrayListOf(item1,item2,item3)
            return Menu("artists")
        }
    }

}