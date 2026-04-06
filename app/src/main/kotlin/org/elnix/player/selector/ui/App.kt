package org.elnix.player.selector.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import org.elnix.player.selector.data.TrackedFinger
import org.elnix.player.selector.ui.helpers.glowOverlay

@SuppressLint("LocalContextResourcesRead")
@Composable
fun App(
    modifier: Modifier
) {

    val ctx = LocalContext.current

    val trackedFingers = remember { mutableStateMapOf<Int, TrackedFinger>() }

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

                        event.changes.forEach { change ->
                            val pointerId = change.id.value.toInt()
                            val position = change.position

                            when {
                                pointerId in trackedFingers -> {
                                    // Finger is moving or still pressed
                                    trackedFingers[pointerId]?.let {
                                        it.position = position
                                        it.lastUpdateTimeMs = System.currentTimeMillis()
                                    }
                                }

                                change.pressed -> {
                                    // Finger just pressed down
                                    if (!trackedFingers.containsKey(pointerId)) {
                                        val now = System.currentTimeMillis()
                                        trackedFingers[pointerId] = TrackedFinger(
                                            pointerId = pointerId,
                                            positionState = mutableStateOf(position),
                                            startTimeMs = now
                                        )
                                    }
                                }
                            }

                            val stillPressed = event.changes.filter { it.pressed }.map { it.id.value.toInt() }.toSet()

                            val toRemove = trackedFingers.keys.filter { it !in stillPressed }

                            toRemove.forEach { pointerId ->
                                trackedFingers.remove(pointerId)
                            }

                            isPressed = trackedFingers.isNotEmpty()
                        }
                    }
                }
            }
    ) {


        if (isPressed) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                trackedFingers.values.forEach { finger ->

                    val offset = finger.position

                    val x = offset.x / screenWidth
                    val y = offset.y / screenHeight

                    val hue = ((x + y) / 2).coerceIn(0f..1f)

                    val color = Color.hsv(hue * 360, 1f, 1f)


                    glowOverlay(offset, color, 150f)
                }
            }
        }

        Text("Displaying ${trackedFingers.size} points:")
    }
}

