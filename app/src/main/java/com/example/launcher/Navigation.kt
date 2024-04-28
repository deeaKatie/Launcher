package com.example.launcher

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.launcher.mainscreen.ui.MainScreen
import com.example.launcher.optionsscreen.ui.OptionsScreen


val launcher_Route = "launcher"
val launcherOptions_Route = "launcher-options"
val lo_apps_list_Route = "launcher-options/apps-list"

@Composable
fun Navigator(activity:Activity) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = launcher_Route
    ) {
        composable(launcher_Route) {
            Log.d("Navigator", "navigate to $launcher_Route")
            MainScreen(onEditClick = {
                Log.d("Navigator", "navigate to $launcherOptions_Route")
                navController.navigate(launcherOptions_Route)
            },
                activity = activity)
        }

        /* ************ LAUNCHER OPTIONS ************ */
        composable(launcherOptions_Route) {
            OptionsScreen(onSaveClick = {
                Log.d("Navigator", "navigate to $launcher_Route")
                navController.navigate(launcher_Route)
            })
        }
        composable(lo_apps_list_Route) {
            OptionsScreen(onSaveClick = {
                Log.d("Navigator", "navigate to $launcherOptions_Route")
                navController.navigate(launcherOptions_Route)
            })
        }
    }
}