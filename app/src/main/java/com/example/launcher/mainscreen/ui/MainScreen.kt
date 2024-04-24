package com.example.launcher.mainscreen.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.launcher.R
import com.example.launcher.widgets.BatteryWidget
import com.example.launcher.widgets.BatteryWidgetLines
import com.example.launcher.widgets.ClockWidget
import com.example.launcher.widgets.DateWidget

@Composable
fun MainScreen(onEditClick: () -> Unit) {
    Log.d("MainScreen", "recompose")

    val context = LocalContext.current
    val appsUiState by AppsViewModel(context).uiState


    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val height = screenHeight / 8
    val width = screenWidth / 4

    val backgroundImage: Painter = painterResource(id = R.drawable.background02)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onEditClick
                ) {
                    Text(
                        text = "EDIT",
                        color = Color.White
                    )
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth())
            {
                Column (
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    // TODO: Battery widget
//                Text("Battery placeholder")
                    BatteryWidgetLines(width)
                }

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .height(height)
                        .width(1.dp))

                Column (
                    modifier = Modifier.height(height)
                ) {
                    ClockWidget(
                        modifier = Modifier
                            .padding(top = height / 8)
                            .fillMaxWidth())
                    DateWidget(
                        modifier = Modifier
                            .fillMaxWidth())

                }
            }

            appsUiState.items?.let { AppsList(appsList = it) }
        }
    }

}
