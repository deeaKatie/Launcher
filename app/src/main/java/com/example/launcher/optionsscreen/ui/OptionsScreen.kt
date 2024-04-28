package com.example.launcher.optionsscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.launcher.mainscreen.ui.AppsViewModel

@Composable
fun OptionsScreen(onSaveClick: () -> Unit, onAppsListEdit: () -> Unit) {

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
        Text(text = "Options",
            modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.size(10.dp))

        //appsUiState.items?.let {AppsListOptions()}

        LazyColumn {

            items (50) {
                OptionsItem(onClick = onAppsListEdit, text = "Modify display apps list")
            }

            item {
                HorizontalDivider()
            }

        }

    }
}


@Composable
fun OptionsItem(onClick: () -> Unit, text: String) {
    HorizontalDivider()
    Row (
        modifier = Modifier.fillMaxSize()
    ) {
        TextButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart // Aligns the text to the left
            ) {
                Text(
                    text = text,
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 16.dp) // Add left padding
                )
            }
        }
    }
}

