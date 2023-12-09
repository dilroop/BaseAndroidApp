package com.dilroopsingh.desigpscamera.ui

import android.Manifest
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilroopsingh.desigpscamera.Navigator
import com.dilroopsingh.desigpscamera.ui.theme.DesiGPSCameraTheme
import com.dilroopsingh.desigpscamera.ui.theme.darkGreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.BuildConfig
import `in`.dilroopsingh.desigpscamera.R

private val cameraPermission = Manifest.permission.CAMERA

private fun storagePermissions() : List<String> {
    return if (Build.VERSION.SDK_INT < 33 ) {
        listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    } else {
        listOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        )
    }
}

private val locationPermissions = listOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermissions(navigator: Navigator, content: @Composable () -> Unit) {
    var cameraPermissionsGranted by remember { mutableStateOf(false) }
    val cameraPermissionsState = rememberPermissionState(
        permission = cameraPermission,
        onPermissionResult = { result -> cameraPermissionsGranted = result }
    )
    cameraPermissionsGranted = cameraPermissionsState.status.isGranted

    var storagePermissionGranted by remember { mutableStateOf(false) }
    val storagePermissionState = rememberMultiplePermissionsState(
        permissions = storagePermissions(),
        onPermissionsResult = { map ->
            val allGranted = storagePermissions().fold(initial = true) { accumulator, permission ->
                accumulator && map[permission] ?: false
            }
            storagePermissionGranted = allGranted
        }
    )
    storagePermissionGranted = storagePermissionState.allPermissionsGranted

    var locationPermissionGranted by remember { mutableStateOf(false) }
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = locationPermissions,
        onPermissionsResult = { map ->
            val allGranted = locationPermissions.fold(initial = true) { accumulator, permission ->
                accumulator && map[permission] ?: false
            }
            locationPermissionGranted = allGranted
        }
    )
    locationPermissionGranted = locationPermissionState.allPermissionsGranted

    val canContinue = cameraPermissionsState.status.isGranted
            && storagePermissionState.allPermissionsGranted
            && locationPermissionState.allPermissionsGranted

    if (canContinue) {
        content()
    } else {
        PermissionContent(
            cameraState = cameraPermissionsState,
            cameraPermissionGranted = cameraPermissionsGranted,
            storageState = storagePermissionState,
            storagePermissionGranted = storagePermissionGranted,
            locationState = locationPermissionState,
            locationPermissionGranted = locationPermissionGranted,
            goToSettings = { navigator.goToNotepadAppDetails() }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionContent(
    cameraState: PermissionState,
    cameraPermissionGranted: Boolean,
    storageState: MultiplePermissionsState,
    storagePermissionGranted: Boolean,
    locationState: MultiplePermissionsState,
    locationPermissionGranted: Boolean,
    goToSettings: () -> Unit,
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(DesiGPSCameraTheme.colors.background)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 60.dp, horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = null
            )

            Text(
                text = stringResource(id = R.string.app_name),
                style = DesiGPSCameraTheme.typography.titleMedium,
                color = DesiGPSCameraTheme.colors.onBackground,
            )

            Column(
                modifier = Modifier
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp), clip = false)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = DesiGPSCameraTheme.colors.secondary)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                TitleSubtitle(
                    title = stringResource(id = R.string.camera_permission_title),
                    subtitle = stringResource(id = R.string.camera_permission_description),
                    buttonText = stringResource(id = R.string.permission_allow_button),
                    isSucceeded = cameraPermissionGranted,
                    onClick = {
                        cameraState.launchPermissionRequest()
                    }
                )

                TitleSubtitle(
                    title = stringResource(id = R.string.storage_permission_title),
                    subtitle = stringResource(id = R.string.storage_permission_description),
                    buttonText = stringResource(id = R.string.permission_allow_button),
                    isSucceeded = storagePermissionGranted,
                    onClick = {
                        storageState.launchMultiplePermissionRequest()
                    }
                )

                TitleSubtitle(
                    title = stringResource(id = R.string.location_permission_title),
                    subtitle = stringResource(id = R.string.location_permission_description),
                    buttonText = stringResource(id = R.string.permission_allow_button),
                    isSucceeded = locationPermissionGranted,
                    onClick = { locationState.launchMultiplePermissionRequest() }
                )

                TitleSubtitle(
                    title = stringResource(id = R.string.go_to_settings_title),
                    subtitle = stringResource(id = R.string.go_to_settings_description),
                    buttonText = stringResource(id = R.string.go_to_settings_button),
                    isSucceeded = false,
                    onClick = goToSettings
                )
            }
        }
    }
}


@Composable
fun TitleSubtitle(
    title: String,
    subtitle: String,
    buttonText: String = "",
    onClick: () -> Unit = {},
    isSucceeded: Boolean = false
) {
    Row {
        Column (modifier = Modifier.fillMaxWidth().weight(1f), horizontalAlignment = Alignment.Start) {
            Text(
                text = title,
                color = DesiGPSCameraTheme.colors.onSecondary,
                style = DesiGPSCameraTheme.typography.titleSmall,
            )

            Text(
                text = subtitle,
                style = DesiGPSCameraTheme.typography.bodySmall,
                color = DesiGPSCameraTheme.colors.onSecondary,
            )
        }

        Spacer(modifier = Modifier.width(40.dp))

        if (!isSucceeded) {
            Button(
                modifier = Modifier.padding(top = 8.dp),
                onClick = { onClick() },
            ) {
                Text(text = buttonText, color = DesiGPSCameraTheme.colors.onPrimary)
            }
        } else {
            Button(
                modifier = Modifier.padding(top = 8.dp),
                onClick = { onClick() },
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.check_icon),
                    contentDescription = "check icon",
                    tint = darkGreen,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTitleSubtitle() {
    DesiGPSCameraTheme {
        TitleSubtitle(
            title = stringResource(id = R.string.location_permission_title),
            subtitle = stringResource(id = R.string.location_permission_description),
            buttonText = stringResource(id = R.string.permission_allow_button),
            onClick = { }
        )
    }
}