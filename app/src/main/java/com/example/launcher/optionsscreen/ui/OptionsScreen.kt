package com.example.launcher.optionsscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.launcher.mainscreen.ui.AppsViewModel

@Composable
fun OptionsScreen(onSaveClick: () -> Unit) {

    val context = LocalContext.current
    val appsUiState by AppsViewModel(context).uiState


    Column (
        modifier = Modifier.fillMaxSize()
    ) {
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
        Text(text = "Options Screen",
            modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.size(10.dp))

        appsUiState.items?.let {AppsListOptions(apps = it)}
    }

}
