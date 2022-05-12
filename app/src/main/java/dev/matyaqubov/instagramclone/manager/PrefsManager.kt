package dev.matyaqubov.instagramclone.manager

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {
    val sharedPreferences: SharedPreferences?

    init {
        sharedPreferences = context.getSharedPreferences("instagram_clone.db", Context.MODE_PRIVATE)
    }

    fun storeDeviceToken(token: String?) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString("device_token", token)
        prefsEditor.apply()
    }

    fun loadDeviceToken():String?{
        return sharedPreferences!!.getString("device_token","")
    }

}