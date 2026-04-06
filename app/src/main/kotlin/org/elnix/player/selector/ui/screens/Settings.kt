package org.elnix.player.selector.ui.screens

import androidx.compose.runtime.Composable
import org.elnix.dragonlauncher.settings.stores.SettingsStore
import org.elnix.player.selector.ui.settings.SettingsScaffold
import org.elnix.player.selector.ui.settings.SettingsSwitchRow

@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    SettingsScaffold(
        title = "Settings",
        onBack = onBack
    ) {
        SettingsSwitchRow(
            setting = SettingsStore.debugMode,
            title = "Debug mode",
            description = "Enable or disable the debug mode"
        )
    }
}