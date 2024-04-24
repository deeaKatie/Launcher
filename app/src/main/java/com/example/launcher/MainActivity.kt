package com.example.launcher

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.launcher.ui.theme.LauncherTheme



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

        // HIDE THE STATUS BAR
//        if (Build.VERSION.SDK_INT < 16) {
//            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        }
//
//
//        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
//            // Note that system bars will only be "visible" if none of the
//            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
//            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
//                // Hide the status bar.
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//                // Remember that you should never show the action bar if the
//                // status bar is hidden, so hide that too if necessary.
//                actionBar?.hide()
//            } else {
//                // Hide the status bar.
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//                // Remember that you should never show the action bar if the
//                // status bar is hidden, so hide that too if necessary.
//                actionBar?.hide()
//            }
//        }
//
//
//        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
//            // Check if the navigation bar is visible
//            val isNavBarVisible = visibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == 0
//
//            // If the navigation bar is visible, hide it again
//            if (isNavBarVisible) {
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                        View.SYSTEM_UI_FLAG_FULLSCREEN
//            }
//        }


        // SET THE CONTENT
        setContent {
            LauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
            // HIDE THE STATUS BAR
            if (Build.VERSION.SDK_INT < 16) {
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }

                    // Hide the status bar.
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                    // Remember that you should never show the action bar if the
                    // status bar is hidden, so hide that too if necessary.
                    actionBar?.hide()

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
