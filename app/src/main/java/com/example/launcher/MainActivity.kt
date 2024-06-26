package com.example.launcher

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.launcher.mainscreen.ui.MainScreen
import com.example.launcher.ui.theme.LauncherTheme
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.database.database


class MainActivity : ComponentActivity() {
    private fun hideSystemBars() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // Hide both the status bar and the navigation bar initially
        hideSystemBars()

        // Set a listener to react to system UI visibility changes
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                // If the status bar becomes visible again, hide it immediately
                hideSystemBars()
            }
        }

        // SET THE CONTENT
        setContent {
            Log.d("MainActivity", "onCreate")
            Firebase.analytics.logEvent("START", null)
            LauncherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Navigator(this)
                }
            }
        }

    }
}
