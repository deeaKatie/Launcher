package com.example.launcher.optionsscreen.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.launcher.mainscreen.ui.AppsViewModel

@Composable
fun OptionsScreen(onSaveClick: () -> Unit) {

    val context = LocalContext.current
    val appsUiState by AppsViewModel(context).uiState


    Text(text = "Options Screen")
    Button(onClick = onSaveClick) {
        Text(text = "Save options")
    }

    appsUiState.items?.let {AppsListOptions(apps = it)}
}