package com.example.launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity

data class App(
    val name: String,
    val packageName: String,
    val icon: Drawable?
)

@Composable
fun MainScreen() {


    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)

    val flags = PackageManager.ResolveInfoFlags.of(
        PackageManager.MATCH_ALL.toLong())

    val activities: List<ResolveInfo> =
        LocalContext.current.packageManager.queryIntentActivities(intent, flags)


    val packageManager = LocalContext.current.packageManager

    val installedApps = activities.map { resolveInfo ->
        App(
            name = resolveInfo.loadLabel(packageManager).toString(),
            packageName = resolveInfo.activityInfo.packageName,
            icon = resolveInfo.loadIcon(packageManager)
        )
    }

    val testAppPN = installedApps.get(0).packageName;


    Column {
        installedApps.forEach { app ->
            Text(text = app.name)
        }
    }

}


