package org.elnix.player.selector.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButtonShapes
import androidx.compose.ui.unit.dp

object UiConstants {

    val DRAGON_SHAPE_CORNER_DP = 12.dp
    val PRESSED_DRAGON_SHAPE_CORNER_DP = 5.dp

    val DragonShape = RoundedCornerShape(DRAGON_SHAPE_CORNER_DP)
    val PressedDragonShape = RoundedCornerShape(PRESSED_DRAGON_SHAPE_CORNER_DP)

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    fun dragonShapes(): ButtonShapes =
        ButtonShapes(
            shape = DragonShape,
            pressedShape = PressedDragonShape
        )

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    fun dragonIconButtonShapes(): IconButtonShapes =
        IconButtonShapes(
            shape = DragonShape,
            pressedShape = PressedDragonShape
        )
}