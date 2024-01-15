package com.yes.trackdialogfeature.data.repository.dataSource

import java.io.IOException

import java.net.HttpURLConnection

import java.net.URL




class NetworkDataSource {
    fun isNetworkPathAvailable(path: String): Boolean {
        return try {
            val url = URL(path)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "HEAD"
            urlConnection.connectTimeout = 1000 // выберите таймаут подходящий для вашего приложения
            urlConnection.connect()
            val responseCode = urlConnection.responseCode
            responseCode == HttpURLConnection.HTTP_OK
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}