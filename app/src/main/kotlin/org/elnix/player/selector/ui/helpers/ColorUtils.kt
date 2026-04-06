package org.elnix.player.selector.ui.helpers

import androidx.compose.ui.graphics.Color

object ColorUtils {
    /**
     * Returns a copy of this [Color] with its alpha multiplied by [multiplier].
     *
     * The RGB components remain unchanged. The resulting alpha is computed as:
     * `currentAlpha * multiplier`.
     *
     * This can be used to uniformly increase or decrease transparency while
     * preserving the original opacity proportion.
     *
     * @param multiplier factor applied to the current alpha value
     * @return a copy of this color with the adjusted alpha
     */
    fun Color.alphaMultiplier(multiplier: Float): Color =
        copy(alpha = alpha * multiplier)
}