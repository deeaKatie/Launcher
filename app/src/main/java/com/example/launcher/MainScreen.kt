package com.example.launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


data class App(
    val name: String,
    val packageName: String,
    val icon: Drawable?
)


private fun loadInstalledApps(context: Context) : List<App> {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)

    var activities: List<ResolveInfo>

    try {
        PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
        activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
    } catch (e: NoSuchMethodError) {
        // PackageManager.ResolveInfoFlags.of() not available on older Android versions
        activities = context.packageManager.queryIntentActivities(intent, 0)
    }


    val packageManager = context.packageManager

    return activities.map { resolveInfo ->
        App(
            name = resolveInfo.loadLabel(packageManager).toString(),
            packageName = resolveInfo.activityInfo.packageName,
            icon = resolveInfo.loadIcon(packageManager)
        )
    }

}


@Composable
fun MainScreen() {

    val context = LocalContext.current
    val installedApps = loadInstalledApps(context)
    val packageManager = context.packageManager

    val metrics = DisplayMetrics();
    val h = metrics.heightPixels
    val w = metrics.widthPixels

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = screenWidth / 3
    val smallImageSize = screenWidth / 5
    val halfScreenSize = screenHeight / 2
    val menuHeight = imageSize * 2


    Log.d("HEIGHT VALUE", screenHeight.value.toString())
    Log.d("WIDTH VALUE", screenWidth.value.toString())


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DateWidget()
        Clock()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = imageSize),
            modifier = Modifier
                .height(menuHeight.value.dp)
        ) {
            items(installedApps) { app ->

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
                                startActivity(context, intent, null)
                            }
                        })
                        .size(width = imageSize, height = imageSize)
                )
            }
        }
    }



}

@Composable
fun Clock() {
    val currentTime: MutableState<String> =  remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        while (true) {
            currentTime.value = getCurrentTime()
            delay(1000) // Update every second
        }
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = currentTime.value,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

private fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val currentTime = Calendar.getInstance().time
    return sdf.format(currentTime)
}

@Composable
fun DateWidget() {
    val currentDate: MutableState<String> = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        while (true) {
            currentDate.value = getCurrentDate()
            delay(60000) // Update every minute
        }
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = currentDate.value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}

private fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val currentDate = Calendar.getInstance().time
    return sdf.format(currentDate)
}