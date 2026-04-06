package org.elnix.player.selector.ui.helpers

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import org.elnix.player.selector.data.Constants
import org.elnix.player.selector.data.TrackedFinger
import org.elnix.player.selector.ui.helpers.ColorUtils.alphaMultiplier

fun DrawScope.fingerCircle(finger: TrackedFinger) {

    val center = finger.position
    val progress = finger.progress

//    Log.d("AppDebug", "Progress: $progress")

//    val screenHeight = this.size.height
//    val screenWidth = this.size.width
//
//    val x = center.x / screenWidth
//    val y = center.y / screenHeight

//    val hue = ((x + y) / 2).coerceIn(0f..1f)
//    val hueColor = Color.hsv(hue * 360, 1f, 1f)

//    val color = Color.hsv(progress * 360, 1f, 1f)
    val color = finger.color ?: Color.White.alphaMultiplier(0.5f)

    val radius = Constants.FINGER_RADIUS_DP.toPx()
    val breatheRadius = radius * (1f + 0.1f * kotlin.math.sin(finger.durationMs / 300f))


    val arcRadius = breatheRadius + 10.dp.toPx()

    val rect = Rect(
        center.x - arcRadius,
        center.y - arcRadius,
        center.x + arcRadius,
        center.y + arcRadius
    )

    val rotationAngle = (finger.durationMs / 2f) % 360f


    drawArc(
        color = color,
        startAngle = rotationAngle,
        sweepAngle = progress * 360,
        useCenter = false,
        topLeft = rect.topLeft,
        size = Size(rect.width, rect.height),
        style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
    )

    drawCircle(
        color = color,
        center = center,
        radius = breatheRadius
    )
}
