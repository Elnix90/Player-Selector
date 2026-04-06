package org.elnix.player.selector.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SettingsTitle(
    title: String,
    onBack: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        DragonIconButton(
            onClick = onBack,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}
