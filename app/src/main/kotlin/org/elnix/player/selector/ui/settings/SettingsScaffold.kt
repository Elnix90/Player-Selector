package org.elnix.player.selector.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elnix.player.selector.ui.helpers.conditional

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScaffold(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    scrollableContent: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    BackHandler {
        onBack()
    }

    Box(
        modifier = Modifier
            .fullScreenStatusBarsPaddings()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SettingsTitle(title) { onBack() }

            Spacer(Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .conditional(scrollableContent) {
                        verticalScroll(rememberScrollState())
                    },
                content = content
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Modifier.fullScreenStatusBarsPaddings(): Modifier =
    this
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.statusBarsIgnoringVisibility)
