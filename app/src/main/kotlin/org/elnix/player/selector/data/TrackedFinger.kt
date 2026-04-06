package org.elnix.player.selector.data

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset

data class TrackedFinger(
    var positionState: MutableState<Offset>,
    val pointerId: Int,
    val startTimeMs: Long,
    var lastUpdateTimeMs: Long = startTimeMs
) {
    var position: Offset
        get() = positionState.value
        set(value) {
            positionState.value = value
        }

    val durationMs: Long
        get() = System.currentTimeMillis() - startTimeMs
}