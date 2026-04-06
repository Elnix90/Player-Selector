package org.elnix.dragonlauncher.base.color

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

// ───────────── Utility: convert Color → #AARRGGBB ─────────────
fun Color?.toHexWithAlpha(prefix: Boolean = true): String =
    "${if (prefix) "#" else ""}%08X".format(this?.toArgb())
