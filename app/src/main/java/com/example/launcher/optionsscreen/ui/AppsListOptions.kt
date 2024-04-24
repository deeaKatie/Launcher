package com.example.launcher.optionsscreen.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.launcher.mainscreen.data.App

@Composable
fun AppsListOptions(apps: List<App>) {
    Log.d("AppsListOptions", "recompose")

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = screenWidth / 10

    val context = LocalContext.current
    val packageManager = context.packageManager

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(apps) { app ->

            Row {
                Text(text = app.name)

                val drawable = app.icon

                val bitmap = Bitmap.createBitmap(
                    imageSize.value.toInt(),
                    imageSize.value.toInt(),
                    Bitmap.Config.ARGB_8888
                )

                val canvas = Canvas(bitmap)
                drawable?.setBounds(0, 0, canvas.width, canvas.height)
                drawable?.draw(canvas)
                val imageBitmap = bitmap.asImageBitmap()
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Adaptive Icon",
                    modifier = Modifier
                        .clickable(onClick = {
                            val intent = packageManager.getLaunchIntentForPackage(app.packageName)
                            if (intent != null) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                ContextCompat.startActivity(context, intent, null)
                            }
                        })
                        .size(width = imageSize, height = imageSize)
                )
            }

        }
    }

}