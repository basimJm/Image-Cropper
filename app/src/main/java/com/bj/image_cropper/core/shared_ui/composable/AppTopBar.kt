package com.bj.image_cropper.core.shared_ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bj.image_cropper.R
import com.bj.image_cropper.core.domain.entity.AppBarAction
import com.bj.image_cropper.core.shared_ui.theme.ImageCropperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onBackPressed: (() -> Unit)? = {},
    title: @Composable () -> Unit = {},
    actions: List<AppBarAction>? = null,
    insets: WindowInsets = WindowInsets(top = 0.dp),
) {
    val backIcon by remember {
        mutableStateOf(Icons.AutoMirrored.Outlined.KeyboardArrowLeft)
    }
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
        ),
        windowInsets = insets,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                title()
            }
        },
        actions = {
            actions?.forEach { action ->
                IconButton(onClick = action.onClicked) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        navigationIcon = {
            if (onBackPressed != null) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onBackPressed()
                        }
                        .size(40.dp)
                        .padding(0.dp),
                    imageVector = backIcon,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
    )
}

@Preview
@Composable
private fun AppTopBarPreview() {
    ImageCropperTheme {
        AppTopBar(
            onBackPressed = {}, title = {},
            actions = listOf(),
        )
    }
}