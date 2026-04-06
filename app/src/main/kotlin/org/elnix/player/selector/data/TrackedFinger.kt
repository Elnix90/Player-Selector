package org.elnix.player.selector.data

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class TrackedFinger(
    var positionState: MutableState<Offset>,
    val pointerId: Int,
    val startTimeMs: Long,
    var lastUpdateTimeMs: Long = startTimeMs,
    var color: Color? = null
) {
    var position: Offset
        get() = positionState.value
        set(value) {
            positionState.value = value
        }

    val durationMs: Long
        get() = System.currentTimeMillis() - lastUpdateTimeMs


    val progress: Float
        get() = (this.durationMs.toFloat() / Constants.GLOBAL_HOLD_DURATION_MS).coerceIn(0f..1f)
}