package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.repository.dataSource.NetworkDataSource

class NetworkRepository(
    private val networkDataSource: NetworkDataSource
) {
    fun checkNetworkPathAvailable(path:String):Boolean{
        return networkDataSource.isNetworkPathAvailable(path)
    }
}