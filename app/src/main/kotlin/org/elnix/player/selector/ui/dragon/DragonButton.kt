package org.elnix.dragonlauncher.ui.components.dragon

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroupScope
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.elnix.player.selector.ui.UiConstants
import org.elnix.player.selector.ui.helpers.withHaptic


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Suppress("AssignedValueIsNeverRead")
@Composable
fun DragonButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {

    Button(
        modifier = modifier,
        onClick = withHaptic {
            onClick()
        },
        shapes = UiConstants.dragonShapes(),
        enabled = enabled,
        content = content
    )
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ButtonGroupScope.DragonButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Button(
        modifier = modifier
            .weight(1f)
            .animateWidth(interactionSource),
        onClick = withHaptic {
            onClick()
        },
        shapes = UiConstants.dragonShapes(),
        enabled = enabled,
        content = content
    )
}