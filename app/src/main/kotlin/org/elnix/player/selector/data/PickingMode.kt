package org.elnix.player.selector.data


sealed class PickingMode {

    data object Single : PickingMode()
    data class Team(
        val teamNumber: Int
    ): PickingMode()

    data object Order : PickingMode()
}
