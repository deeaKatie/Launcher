package com.example.launcher.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun DateWidget(modifier: Modifier) {
    val currentDate: MutableState<String> = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        while (true) {
            currentDate.value = getCurrentDate()
            delay(60000) // Update every minute
        }
    }

    Box(
        modifier = modifier,
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
    val sdf = SimpleDateFormat("EE, MMMM dd", Locale.getDefault())
    val currentDate = Calendar.getInstance().time
    return sdf.format(currentDate)
}