package com.example.launcher.widgets

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun BatteryWidget(size: Dp) {
    val batteryLevel: Int = getBatteryLevel(LocalContext.current)

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "$batteryLevel%"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(
            modifier = Modifier.size(size),
            onDraw = {
                val widthPx = size.toPx()
                val heightPx = size.toPx() / 2

                drawRoundRect(
                    color = Color.LightGray,
                    topLeft = Offset(0f, 0f),
                    size = Size(widthPx, heightPx),
                    cornerRadius = CornerRadius(8f, 8f)
                )
                val batteryWidth = batteryLevel * 0.01f * (widthPx - 10) // Adjust width based on battery level
                drawRoundRect(
                    color = Color.Green,
                    topLeft = Offset(5f, 5f),
                    size = Size(batteryWidth, heightPx - 10),
                    cornerRadius = CornerRadius(5f, 5f)
                )
                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = Offset(widthPx - 5, heightPx - 5),
                    size = Size(10f, 10f),
                    cornerRadius = CornerRadius(5f, 5f),
                    style = Stroke(width = 1f)
                )
            }
        )
    }
}

@Composable
fun BatteryWidgetLines(size: Dp) {
    val batteryLevel: Int = getBatteryLevel(LocalContext.current)
    val maxLineSize = 20
    val numSegments = 5
    val numCurrentSegments:Int = if (batteryLevel <= 15) {
        0                                   // show no lines is battery < 15
    } else {
        batteryLevel / maxLineSize       // show as many lines as battery lvl
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "$batteryLevel%"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(
            modifier = Modifier.size(size),
            onDraw = {
                val widthPx = size.toPx()
                val heightPx = size.toPx() / 2

                drawRoundRect(
                    color = Color.Gray,
                    topLeft = Offset(0f, 0f),
                    size = Size(widthPx, heightPx),
                    cornerRadius = CornerRadius(8f, 8f)
                )

                val totalSpacing = 10.dp.toPx() // Total spacing between segments
                val segmentWidth = (widthPx - totalSpacing) / numSegments
                val spacingBetweenSegments = totalSpacing / (numSegments - 1)

                for (i in 0 until numCurrentSegments) {
                    val segmentColor = if (i < (batteryLevel.toFloat() / 100 * numSegments)) {
                        Color.Green
                    } else {
                        Color.Transparent
                    }

                    drawRoundRect(
                        color = segmentColor,
                        topLeft = Offset(i * (segmentWidth + spacingBetweenSegments), 5f),
                        size = Size(segmentWidth, heightPx - 10),
                        cornerRadius = CornerRadius(5f, 5f)
                    )
                }

                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = Offset(widthPx - 5, heightPx - 5),
                    size = Size(10f, 10f),
                    cornerRadius = CornerRadius(5f, 5f),
                    style = Stroke(width = 1f)
                )
            }
        )
    }
}

private fun getBatteryLevel(context: Context): Int {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
        context.registerReceiver(null, ifilter)
    }
    val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
    return (level * 100 / scale)
}

