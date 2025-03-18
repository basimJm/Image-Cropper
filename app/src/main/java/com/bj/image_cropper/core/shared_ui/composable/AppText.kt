package com.bj.image_cropper.core.shared_ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelSmall.copy(
        color = MaterialTheme.colorScheme.onBackground,
    ),
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onClick: () -> Unit = {},
    clickable: Boolean = false,
) {
    Text(
        modifier = if (clickable) modifier.clickable { onClick() } else modifier,
        text = text,
        style = style,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        minLines = minLines,
    )
}
