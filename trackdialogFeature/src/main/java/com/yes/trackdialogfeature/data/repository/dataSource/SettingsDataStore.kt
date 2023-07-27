package com.yes.trackdialogfeature.data.repository.dataSource

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString

class SettingsDataStore(private val activity: Activity) {
    fun putString(key:String, value:String){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }
    fun getString(name:String):String?{
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return null
        return sharedPref.getString(name, null)
    }

}