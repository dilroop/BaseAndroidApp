package com.dilroopsingh.desigpscamera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.navigation.NavHostController
import java.net.URL


interface Navigator {
    fun goToNotepadAppDetails()
    fun goToFileManagerPermission()
    fun openLink(url: URL)
    fun closeApp()
}

class NavigatorImpl(
    private val activity: Activity,
    private val navHostController: NavHostController
) : Navigator {

    override fun goToNotepadAppDetails() {
        activity.startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.packageName, null)
            )
        )
    }

    override fun goToFileManagerPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                activity.startActivity(intent)
            }
        }
    }

    override fun openLink(url: URL) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()) )
        activity.startActivity(browserIntent)
    }

    override fun closeApp() {
        activity.finish()
    }
}

class FakeNavigator : Navigator {
    override fun goToNotepadAppDetails() {}
    override fun goToFileManagerPermission() {}
    override fun openLink(url: URL) {}
    override fun closeApp() {}
}