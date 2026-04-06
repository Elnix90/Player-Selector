package org.elnix.player.selector.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.elnix.player.selector.data.Constants
import org.elnix.player.selector.data.PickingMode
import org.elnix.player.selector.data.TrackedFinger
import org.elnix.player.selector.ui.helpers.fingerCircle

@Composable
fun App(onSettings: () -> Unit) {

    val haptic = LocalHapticFeedback.current

    val trackedFingers = remember { mutableStateMapOf<Int, TrackedFinger>() }
    var isPressed by remember { mutableStateOf(false) }
    var recompositionTrigger by remember { mutableIntStateOf(0) }

    val areAllFingersMaxProgress = trackedFingers.isNotEmpty() && trackedFingers.values.all {
        Log.d("AppDebug", "Progress: ${it.progress}")
        it.progress == 1f
    }

    var isInPauseMode by remember { mutableStateOf(false) }

    val currentPickingMode: PickingMode = Constants.CURRENT_PICKING_MODE



    LaunchedEffect(areAllFingersMaxProgress) {
        // Need at least 2 fingers to determine colors
        if (areAllFingersMaxProgress && trackedFingers.size > 1) {

            // What the code do when the loading ends


            var canProceed = true

            when (currentPickingMode) {

                PickingMode.Single -> {
                    trackedFingers.values.random().color = Color.Red
                }

                is PickingMode.Team -> {
                    val teamNumber = currentPickingMode.teamNumber

                    if (trackedFingers.size % teamNumber != 0) {
                        canProceed = false
                    } else {

                        val playersPerTeam = trackedFingers.size / teamNumber
                        val shuffledIds = trackedFingers.keys.shuffled()

                        val colors = listOf(
                            Color.Red,
                            Color.Gray,
                            Color.Blue,
                            Color.Yellow,
                            Color.Cyan,
                            Color.Magenta
                        )

                        // Assumes teamNumber <= 7
                        for (teamIdx in 0 until teamNumber) {
                            for (playerIdx in 0 until playersPerTeam) {
                                val fingerId = shuffledIds[teamIdx * playersPerTeam + playerIdx]
                                trackedFingers[fingerId]?.color = colors[teamIdx]
                            }
                        }
                    }
                }

                PickingMode.Order -> TODO()
            }

            if (canProceed) {
                haptic.performHapticFeedback(HapticFeedbackType.Reject)
                isInPauseMode = true
            } else {
                // Reset the timers in case of an error (not the right number of fingers
                trackedFingers.values.forEach {
                    it.lastUpdateTimeMs = System.currentTimeMillis()
                }
            }
        }
    }

    if (isPressed) {
        LaunchedEffect(Unit) {
            while (true) {
                recompositionTrigger++
                delay(10)
            }
        }
    }


    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {

                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Main)


                        if (!isInPauseMode) {
                            event.changes.forEach { change ->
                                change.consume()

                                val pointerId = change.id.value.toInt()
                                val position = change.position

                                when {
                                    pointerId in trackedFingers -> {
                                        trackedFingers[pointerId]?.let {
                                            it.position = position
                                        }
                                    }

                                    change.pressed -> {
                                        trackedFingers[pointerId] = TrackedFinger(
                                            pointerId = pointerId,
                                            positionState = mutableStateOf(change.position),
                                            startTimeMs = System.currentTimeMillis()
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
            .pointerInput(areAllFingersMaxProgress) {
                detectTapGestures {
                    if (areAllFingersMaxProgress) {
                        isInPauseMode = false
                        isPressed = false
                        trackedFingers.clear()
                    }
                }
            }
    ) {


        if (isPressed) {

            key(recompositionTrigger) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    trackedFingers.values.forEach { finger ->
                        fingerCircle(finger = finger)
                    }
                }
            }
        }


        AnimatedVisibility(!isPressed) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clickable { onSettings() }
            )
        }

        Column {
            Text("Displaying ${trackedFingers.size} points:")
            Text("Are all points finished loading $areAllFingersMaxProgress")
            Text("Is in pause mode: $isInPauseMode")
        }
    }
}

