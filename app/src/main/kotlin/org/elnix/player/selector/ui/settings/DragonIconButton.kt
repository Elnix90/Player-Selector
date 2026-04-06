package org.elnix.player.selector.ui.settings

import androidx.compose.animation.Crossfade
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DragonIconButtonImpl(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = content
    )
}

@Composable
fun DragonIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: () -> Boolean = { true },
    imageVector: ImageVector,
    contentDescription: String
) {

    DragonTooltip(contentDescription) {
        DragonIconButtonImpl(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled()
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}


@Composable
fun ToggleableDragonIconButton(
    onClick: () -> Unit,
    toggled: () -> Boolean,
    modifier: Modifier = Modifier,
    enabled: () -> Boolean = { true },
    imageVectorEnabled: ImageVector,
    imageVectorDisabled: ImageVector,
    contentDescription: String
) {

    DragonTooltip(contentDescription) {
        DragonIconButtonImpl(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled()
        ) {
            Crossfade(toggled()) {
                if (it) {
                    Icon(
                        imageVector = imageVectorEnabled,
                        contentDescription = contentDescription
                    )
                } else {
                    Icon(
                        imageVector = imageVectorDisabled,
                        contentDescription = contentDescription
                    )
                }
            }
        }
    }
}
