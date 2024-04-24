package com.example.launcher

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.launcher.mainscreen.ui.MainScreen
import com.example.launcher.optionsscreen.ui.OptionsScreen


val launcherRoute = "launcher"
val editLauncherRoute = "edit-launcher"

@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = launcherRoute
    ) {
        composable(launcherRoute) {
            Log.d("Navigator", "navigate to $launcherRoute")
            MainScreen(onEditClick = {
                Log.d("Navigator", "navigate to $editLauncherRoute")
                navController.navigate(editLauncherRoute)
            })
        }
        composable(editLauncherRoute) {
            OptionsScreen(onSaveClick = {
                Log.d("Navigator", "navigate to $launcherRoute")
                navController.navigate(launcherRoute)
            })
        }
    }
}