package com.example.launcher.optionsscreen.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        items(apps) { app ->
            HorizontalDivider()
            AppsListItem(app)
        }
    }
}

@Composable
fun AppsListItem(app: App) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = screenWidth / 6

    val context = LocalContext.current
    val packageManager = context.packageManager

    // IMAGE
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


    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

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
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = app.name,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = true,
            onCheckedChange = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )


    }
}