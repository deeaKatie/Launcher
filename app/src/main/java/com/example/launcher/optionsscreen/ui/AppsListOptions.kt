package com.example.launcher.optionsscreen.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.service.autofill.OnClickAction
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.example.launcher.mainscreen.data.App
import com.example.launcher.mainscreen.ui.AppsUiState
import kotlinx.coroutines.launch

@Composable
fun AppsListOptions(onSaveClick: () -> Unit) {
    Log.d("AppsListOptions", "recompose")

    val apps = loadItems(LocalContext.current)

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onSaveClick
            ) {
                Text(
                    text = "SAVE",
                    color = Color.Gray
                )
            }
        }
        Text(
            text = "Modify display apps",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.size(10.dp))

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

    var checked: Boolean by remember {
        mutableStateOf(app.checked)
    }


    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.size(10.dp))
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
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.size(10.dp))

    }
}

fun loadItems(context: Context): List<App> {
    Log.d("AppsUiState", "Loading Apps")

    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)

    val packageManager = context.packageManager

    val activities: List<ResolveInfo> = if (Build.VERSION.SDK_INT >= 33) {
        PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
        context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
    } else {
        context.packageManager.queryIntentActivities(intent, 0)
    }


    return activities.map { resolveInfo ->
        App(
            name = resolveInfo.loadLabel(packageManager).toString(),
            packageName = resolveInfo.activityInfo.packageName,
            icon = resolveInfo.loadIcon(packageManager),
            checked = true
        )
    }

}