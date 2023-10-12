package com.yes.core.repository.dataSource


import android.content.Context


class SettingsSharedPreferences(context: Context) {
    private val sharedPref = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun putString(key:String, value:String){
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }
    fun getString(key:String):String?{
        return sharedPref.getString(key, null)
    }

}