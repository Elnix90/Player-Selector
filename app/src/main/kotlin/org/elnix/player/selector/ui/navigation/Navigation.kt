package org.elnix.player.selector.ui.navigation

import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.elnix.player.selector.ui.screens.App
import org.elnix.player.selector.ui.screens.SettingsScreen

@Composable
fun Navigation(
    modifier: Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.App,
        modifier = modifier,
        enterTransition = { fadeIn(spring()) + scaleIn(initialScale = 1.1f) },
        exitTransition = { fadeOut(spring()) + scaleOut(targetScale = 1.1f) }
    ) {
        composable<Screen.App> {
            App { navController.navigate(Screen.Settings) }
        }

        composable<Screen.Settings> {
            SettingsScreen { navController.popBackStack() }
        }
    }
}