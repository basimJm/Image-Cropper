package com.bj.image_cropper.core.shared_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bj.image_cropper.core.domain.entity.AppBarAction
import com.bj.image_cropper.core.shared_ui.utils.FontProvider.cairoMedium
import com.bj.image_cropper.core.shared_ui.utils.Sp

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)? = null,
    withAppBar: Boolean = true,
    floatingActionButton: @Composable () -> Unit = {},
    title: String = "",
    topBar: @Composable (() -> Unit)? = null,
    actions: List<AppBarAction>? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Scaffold(
        floatingActionButton = floatingActionButton,
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            if (withAppBar)
                AppTopBar(
                    modifier = Modifier
                        .statusBarsPadding(),
                    backgroundColor = backgroundColor,
                    title = {
                        if (title.isNotEmpty())
                            AppText(
                                modifier = Modifier.basicMarquee(),
                                text = title,
                                style = TextStyle(
                                    fontSize = 24.Sp,
                                    textAlign = TextAlign.Start,
                                    fontFamily = cairoMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    lineHeight = 23.Sp,
                                ),
                            )
                    },
                    actions = actions,
                    onBackPressed = onBackPressed,
                )

            topBar?.let { it() }
        },
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(top = if (withAppBar) paddingValues.calculateTopPadding() else 0.dp)
                .navigationBarsPadding()
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}