package com.example.launcher.mainscreen.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launcher.mainscreen.data.App
import kotlinx.coroutines.launch

data class AppsUiState(val items: List<App>?)

class AppsViewModel(context : Context) : ViewModel() {
    var uiState = mutableStateOf(AppsUiState ( null))

    init {
        Log.d("AppsUiState", "Initialize")
        loadItems(context)
    }

    private fun loadItems(context: Context) {
        Log.d("AppsUiState", "Loading Apps")
        viewModelScope.launch {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)

            val packageManager = context.packageManager

            val activities: List<ResolveInfo> = if (Build.VERSION.SDK_INT >= 33) {
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
                context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
            } else {
                context.packageManager.queryIntentActivities(intent, 0)
            }


            uiState.value = AppsUiState(activities.map { resolveInfo ->
                App(
                    name = resolveInfo.loadLabel(packageManager).toString(),
                    packageName = resolveInfo.activityInfo.packageName,
                    icon = resolveInfo.loadIcon(packageManager)
                )
            })
        }
    }

}
