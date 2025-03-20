package com.bj.image_cropper.core.shared_ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bj.image_cropper.core.shared_ui.theme.ImageCropperTheme

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    borderStroke: BorderStroke? = null,
    elevation: Dp = 40.dp,
    cornerRadius: Dp = 3.dp,
    customColor: Color = MaterialTheme.colorScheme.surface,
    customBackgroundImage: Painter? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = CardDefaults.cardColors(
        containerColor = customColor,
    )

    Card(
        modifier = modifier,
        border = borderStroke,
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
        ),
        content = {
            Box {
                if (customBackgroundImage != null) Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = customBackgroundImage,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                )
                Box(
                    modifier = contentModifier
                        .clip(RoundedCornerShape(cornerRadius))
                        .background(customColor),
                ) {
                    content()
                }
            }

        },
        colors = colors,
    )
}

@Preview
@Composable
private fun AppCardPreview() {
    ImageCropperTheme {
        AppCard(
            modifier = Modifier
                .height(113.dp)
                .width(183.dp),
            borderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

            }
        }

    }
}