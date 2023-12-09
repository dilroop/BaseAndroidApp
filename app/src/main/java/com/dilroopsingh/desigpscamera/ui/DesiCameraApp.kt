package com.dilroopsingh.desigpscamera.ui

import androidx.compose.runtime.Composable
import com.dilroopsingh.desigpscamera.Navigator
import com.dilroopsingh.desigpscamera.ui.screens.HomeScreen
import com.dilroopsingh.desigpscamera.ui.theme.DesiGPSCameraTheme
import com.dsb.notepad.ui.StatusBarColor

@Composable
fun DesiCameraApp(
    navigator: Navigator
) {
    DesiGPSCameraTheme {
        StatusBarColor(color = DesiGPSCameraTheme.colors.background)
        CheckPermissions(navigator) {
            HomeScreen(navigator = navigator)
        }
    }
}

