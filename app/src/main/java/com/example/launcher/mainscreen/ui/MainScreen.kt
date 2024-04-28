package com.example.launcher.mainscreen.ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.os.Environment
import android.util.Log
import android.view.WindowManager
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.launcher.R
import com.example.launcher.widgets.BatteryWidget
import com.example.launcher.widgets.BatteryWidgetLines
import com.example.launcher.widgets.ClockWidget
import com.example.launcher.widgets.DateWidget
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Date

@Composable
fun MainScreen(onEditClick: () -> Unit, activity:Activity) {
    Log.d("MainScreen", "recompose")

    val context = LocalContext.current
    val appsUiState by AppsViewModel(context).uiState


    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val height = screenHeight / 8
    val width = screenWidth / 4

    val backgroundImage: Painter = painterResource(id = R.drawable.background02)
    var message : String by remember {
        mutableStateOf("")
    }

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
//                TextField(
//                    value = message,
//                    onValueChange = {message = it},
//                    modifier = Modifier
//                        .width(200.dp)
//                )
//                TextButton(
//                    onClick = { sendToDb(message) }
//                ) {
//                    Text(
//                        text = "SEND",
//                        color = Color.White
//                    )
//                }

                TextButton(
                    onClick = { uploadImageToFirebase(activity) }
                ) {
                    Text(
                        text = "SEND",
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

fun sendToDb(message:String) {
    /* Send msg to firebase */
    val database = Firebase.database
    val myRef = database.getReference("message")

    myRef.push().setValue(message)
        .addOnSuccessListener {
            // Write was successful
            Log.d("FIREBASE", "Message sent successfully")
        }
        .addOnFailureListener { e ->
            // Write failed
            Log.e("FIREBASE", "Error sending message: $e")
        }
}

fun uploadImageToFirebase(activity: Activity) {
    // Get the ss
    val img = takeScreenshot(activity)

    // Get a reference to Firebase Storage
    val database = Firebase.database
    val storageRef = database.reference

    // Create a reference to the image file you want to upload
    val imagesRef = storageRef.child("images/${img.name}")



}

fun takeScreenshot(activity: Activity): File {
    val now = Date()
    val date = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

    val v1 = activity.window.decorView.rootView
    v1.isDrawingCacheEnabled = true
    val bitmap = Bitmap.createBitmap(v1.drawingCache)
    v1.isDrawingCacheEnabled = false

    val imageFile = File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "screenshot-$date.png")

    val outputStream = FileOutputStream(imageFile)
    val quality = 100
    bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
    outputStream.flush()
    outputStream.close()

    return imageFile
}