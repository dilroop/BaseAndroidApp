package com.dilroopsingh.desigpscamera.preferences

import android.content.Context
import android.content.SharedPreferences
import java.io.File
import javax.inject.Inject

interface Preferences {
    fun getSettings(): Storeable<DesiCameraSettings>
}

class Prefs @Inject constructor(
    private val context: Context
) : Preferences {

    override fun getSettings(): Storeable<DesiCameraSettings> {
        val preferences: SharedPreferences = context.getSharedPreferences(
            SettingSaver.FILE_NAME,
            Context.MODE_PRIVATE
        )
        return SettingSaver(preferences = preferences)
    }
}
