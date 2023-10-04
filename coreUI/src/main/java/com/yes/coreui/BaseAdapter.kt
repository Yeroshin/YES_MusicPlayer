package com.yes.coreui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface  BaseAdapter {
     var itemList:ArrayList<BaseAdapterItem>

    fun setItems(items:ArrayList<BaseAdapterItem>){
        itemList=items
    }



}