package com.dilroopsingh.desigpscamera.preferences

import android.content.SharedPreferences
import androidx.core.content.edit

data class DesiCameraSettings(
    val manualMode: Boolean
) {
    companion object {
        const val MANUAL_MODE = "manual_mode"
    }
}

class SettingSaver (
    private val preferences: SharedPreferences
) : Storeable<DesiCameraSettings> {
    override fun get(): DesiCameraSettings {
        val manualMode = preferences.getBoolean(DesiCameraSettings.MANUAL_MODE, false)

        return DesiCameraSettings(manualMode = manualMode)
    }

    override fun store(value: DesiCameraSettings) {
        preferences.edit {
            putBoolean(DesiCameraSettings.MANUAL_MODE, value.manualMode)
        }
    }

    companion object {
        const val FILE_NAME = "desi_camera_settings"
    }
}