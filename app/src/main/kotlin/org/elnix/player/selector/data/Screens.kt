package org.elnix.player.selector.data

sealed class Screens {
    data object Main : Screens()
    data object Settings : Screens()
}