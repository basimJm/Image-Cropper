package com.bj.image_cropper.feature.image_editor.presentation.ui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bj.image_cropper.R
import com.bj.image_cropper.core.shared_ui.theme.md_theme_button_disabled
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.eq0
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.setAspect

private fun Size.isAspect(aspect: AspectRatio): Boolean {
    return ((width / height) - (aspect.x.toFloat() / aspect.y)).eq0()
}

internal val LocalVerticalControls = staticCompositionLocalOf { false }

@Composable
internal fun CropperControls(
    state: CropState,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                clip = false,
                ambientColor = Color.Black,
                spotColor = Color.Black,
            ),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row(modifier.horizontalScroll(rememberScrollState())) {
                val aspects = LocalCropperStyle.current.aspects
                val unselectedTint = MaterialTheme.colorScheme.surface
                val selectedTint = md_theme_button_disabled

                IconButton(onClick = { state.reset() }) {
                    Icon(
                        painter = painterResource(R.drawable.restore),
                        tint = unselectedTint,
                        contentDescription = stringResource(
                            R.string.restore_icon
                        )
                    )
                }

                IconButton(onClick = {
                    state.reset()
                    state.region = state.region.setAspect(aspects[2])
                }) {
                    val isSelected = state.region.size.isAspect(aspects[2])
                    Text(
                        "${aspects[2].x}:${aspects[2].y}",
                        color = if (isSelected) selectedTint else unselectedTint
                    )
                }
                IconButton(onClick = {
                    state.reset()
                    state.region = state.region.setAspect(aspects[1])
                }) {
                    val isSelected = state.region.size.isAspect(aspects[1])
                    Text(
                        "${aspects[1].x}:${aspects[1].y}",
                        color = if (isSelected) selectedTint else unselectedTint
                    )
                }
                IconButton(onClick = {
                    state.reset()
                    state.region = state.region.setAspect(aspects[0])
                }) {
                    val isSelected = state.region.size.isAspect(aspects[0])
                    Text(
                        "${aspects[0].x}:${aspects[0].y}",
                        color = if (isSelected) selectedTint else unselectedTint
                    )
                }
                IconButton(onClick = {
                    state.reset()
                    state.region = state.region.setAspect(aspects[3])
                }) {
                    val isSelected = state.region.size.isAspect(aspects[3])
                    Text(
                        "${aspects[3].x}:${aspects[3].y}",
                        color = if (isSelected) selectedTint else unselectedTint
                    )
                }
                IconButton(onClick = {
                    state.reset()
                    state.region = state.region.setAspect(aspects[4])
                }) {
                    val isSelected = state.region.size.isAspect(aspects[4])
                    Text(
                        "${aspects[4].x}:${aspects[4].y}",
                        color = if (isSelected) selectedTint else unselectedTint
                    )
                }
                IconButton(onClick = {
                    state.reset()
                    state.region = state.region.setAspect(aspects[5])
                }) {
                    val isSelected = state.region.size.isAspect(aspects[5])
                    Text(
                        "${aspects[5].x}:${aspects[5].y}",
                        color = if (isSelected) selectedTint else unselectedTint
                    )
                }
                IconButton(onClick = {
                    state.reset()
                    state.region = state.region.setAspect(aspects[6])
                }) {
                    val isSelected = state.region.size.isAspect(aspects[6])
                    Text(
                        "${aspects[6].x}:${aspects[6].y}",
                        color = if (isSelected) selectedTint else unselectedTint
                    )
                }
                IconButton(onClick = {
                    state.reset()
                    state.region = state.region.setAspect(aspects[7])
                }) {
                    val isSelected = state.region.size.isAspect(aspects[7])
                    Text(
                        "${aspects[7].x}:${aspects[7].y}",
                        color = if (isSelected) selectedTint else unselectedTint
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}