package com.yes.trackdialogfeature.data.repository.dataSource

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString

class SettingsDataStore(context: Context) {
    private val sharedPref = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun putString(key:String, value:String){
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }
    fun getString(name:String):String?{
        return sharedPref.getString(name, null)
    }

}