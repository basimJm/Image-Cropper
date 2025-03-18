package com.bj.image_cropper.core.shared_ui.utils

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsHandler(
    permissions: List<String>,
    messageResource: Int,
    content: @Composable () -> Unit = { },
) {
    val permissionStates = permissions.map { rememberPermissionState(it) }

    val allPermissionsGranted = permissionStates.all { it.status.isGranted }

    var showDialog by remember { mutableStateOf(false) }

    if (allPermissionsGranted) {
        content()
    } else {
        val deniedPermissions = permissionStates.filter { !it.status.isGranted }

        if (deniedPermissions.isNotEmpty() && !showDialog) {
            showDialog = true
        }

        deniedPermissions.forEach { permissionState ->
            Log.d(
                "PermissionsHandler",
                "All permissions granted: ${permissionState.permission}"
            )

            Rationale(
                text = stringResource(messageResource)
            ) {
                permissionState.launchPermissionRequest()
                showDialog = false
            }
        }
    }
}


@Composable
private fun Rationale(
    text: String,
    onRequestPermissions: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { /* Prevent dismiss */ },
        title = {
            Text(text = "asdasdas")
        },
        text = {
            Text(text = text)
        },
        confirmButton = {
            Button(onClick = onRequestPermissions) { // Requests all missing permissions
                Text(text = "OK")
            }
        },
    )
}