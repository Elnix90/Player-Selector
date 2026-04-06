package org.elnix.player.selector.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object App : Screen()

    @Serializable
    data object Settings : Screen()
}