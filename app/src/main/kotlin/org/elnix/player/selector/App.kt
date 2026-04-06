package org.elnix.player.selector

import android.R.attr.x
import android.R.attr.y
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.elnix.player.selector.ui.theme.glowOverlay
import kotlin.math.hypot


const val THRESHOLD = 50f

@SuppressLint("LocalContextResourcesRead")
@Composable
fun App(
    modifier: Modifier
) {

    val ctx = LocalContext.current

    val pressedPoints = remember { mutableStateListOf<Offset>() }

    val screenHeight = ctx.resources.displayMetrics.heightPixels
    val screenWidth = ctx.resources.displayMetrics.widthPixels


    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {

                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)

                        val changes = event.changes

//                        Log.d("AppDebug", "Got event; ${event.changes.size}\n")

                        if (changes.isNotEmpty()) {
                            event.changes.forEach {

//                                Log.d("AppDebug", "Position: ${it.position}")

                                val current = it.position

                                var closest: Offset? = null
                                var best = Float.MAX_VALUE

                                pressedPoints.forEach { offset ->

                                    val dxVal = current.x - offset.x
                                    val dyVal = current.y - offset.y
                                    val dist = hypot(dxVal, dyVal)

                                    if (dist < best) {
                                        best = dist
                                        closest = offset
                                    }
                                }

                                closest = if (best <= THRESHOLD) closest else null

                                if (closest != null) {

                                    val index = pressedPoints.indexOf(closest)
                                    pressedPoints.remove(closest)

                                    pressedPoints.add(index, it.position, )
                                } else {
                                    pressedPoints.add(it.position)
                                }

                            }
                            isPressed = true
                        }

                        if (changes.all { !it.pressed }) {
                            isPressed = false
                            pressedPoints.clear()

                        }
                    }
                }
            }
    ) {





        if (isPressed) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                pressedPoints.forEach { offset ->

                    val x = offset.x / screenWidth
                    val y = offset.y / screenHeight

                    val hue = ((x + y) / 2).coerceIn(0f..1f)

//                Log.d("AppDebug", "x: $x")
//                Log.d("AppDebug", "y: $y")
//                Log.d("AppDebug", "Hue: $hue")


                    val color = Color.hsv(hue * 360, 1f, 1f)


                    glowOverlay(offset, color, 150f)
                }
            }
        }

        Text("Displaying ${pressedPoints.size} points:")

    }
}

