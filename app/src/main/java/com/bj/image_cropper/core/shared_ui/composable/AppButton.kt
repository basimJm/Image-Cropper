package com.bj.image_cropper.core.shared_ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bj.image_cropper.R
import com.bj.image_cropper.core.shared_ui.theme.ImageCropperTheme
import com.bj.image_cropper.core.shared_ui.theme.md_theme_button_disabled
import com.bj.image_cropper.core.shared_ui.utils.Sp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonHeight: Dp = 60.dp,
    trailingIcon: ImageVector? = null,
    leadingIcon: ImageVector? = null,
    elevation: Dp = 0.dp,
    border: BorderStroke? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontSize = 22.Sp,
        textAlign = TextAlign.Center,
    ),
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    buttonColor: Color = MaterialTheme.colorScheme.primaryContainer,
    cornerRadius: Dp = 5.dp,
    onClick: () -> Unit,
) {
    ElevatedButton(
        border = border,
        modifier = modifier
            .height(buttonHeight),
        onClick = onClick,
        shape = RoundedCornerShape(cornerRadius),
        enabled = enabled && !isLoading,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = elevation,
        ),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            containerColor = buttonColor,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (!isLoading) {
                leadingIcon?.let {
                    Icon(imageVector = it, contentDescription = "")
                }

                AppText(
                    text = title,
                    style = textStyle.copy(
                        color = textColor,
                    ),
                )

                trailingIcon?.let {
                    Icon(imageVector = it, contentDescription = "")
                }
            } else {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}

@Composable
fun AppSecondaryButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonHeight: Dp = 60.dp,
    buttonColor: Color = Color.Transparent,
    buttonSecondaryColor: Color = MaterialTheme.colorScheme.primaryContainer,
    cornerRadius: Dp = 5.dp,
    elevation: Dp = 40.dp,
    onClick: () -> Unit,
) {
    val border = BorderStroke(
        1.dp,
        if (enabled) buttonSecondaryColor else md_theme_button_disabled,
    )

    AppCard(
        modifier = modifier
            .height(buttonHeight)
            .background(buttonColor)
            .clickable {
                if (enabled)
                    onClick()
            },
        borderStroke = border,
        cornerRadius = cornerRadius,
        elevation = elevation,
        customColor = MaterialTheme.colorScheme.background,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (!isLoading) {
                AppText(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 20.Sp,
                        color = if (enabled) buttonSecondaryColor else md_theme_button_disabled,
                    ),
                )
            } else {
                CircularProgressIndicator(color = buttonSecondaryColor)
            }
        }
    }
}

@Preview
@Composable
private fun AppSecondaryButtonPreview() {
    ImageCropperTheme(
        darkTheme = false,
    ) {
        AppSecondaryButton(
            cornerRadius = 10.dp,
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.preview_test_text),
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun AppButtonPreview() {
    ImageCropperTheme(
        darkTheme = false,
    ) {
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.preview_test_text),
            isLoading = false,
        ) {}
    }
}