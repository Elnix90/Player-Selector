@file:Suppress("AssignedValueIsNeverRead")

package org.elnix.player.selector.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import org.elnix.dragonlauncher.settings.bases.BaseSettingObject
import org.elnix.player.selector.ui.helpers.SwitchRow
import org.elnix.player.selector.ui.helpers.asState

@Composable
fun SettingsSwitchRow(
    setting: BaseSettingObject<Boolean, Boolean>,
    title: String,
    description: String,
    enabled: Boolean = true,
    onCheck: ((Boolean) -> Unit)? = null
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    val state by setting.asState()

    var tempState by remember { mutableStateOf(state) }

    LaunchedEffect(state) { tempState = state }

    fun toggle(state: Boolean) {
        tempState = state
        scope.launch {
            setting.set(ctx, state)
        }
        onCheck?.invoke(state)
    }

    SwitchRow(
        state = tempState,
        title = title,
        description = description,
        enabled = enabled
    ) { clicked ->
        toggle(clicked)

    }
}
