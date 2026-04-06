package org.elnix.player.selector.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.elnix.player.selector.ui.settings.SettingsScaffold

@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {


    SettingsScaffold(
        title = "Settings",
        onBack = onBack
    ) {
        Text("Settings!")
    }
}