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
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.AnimatedStateListDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.DrawableWrapper
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.LevelListDrawable
import android.graphics.drawable.NinePatchDrawable
import android.graphics.drawable.PictureDrawable
import android.graphics.drawable.RotateDrawable
import android.graphics.drawable.ScaleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.TransitionDrawable
import android.graphics.drawable.VectorDrawable
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity


import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter


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

    val installedApps = loadInstalledApps(LocalContext.current)

    val metrics = DisplayMetrics();
    val h = metrics.heightPixels / 11
    val w = metrics.widthPixels / 4

    LazyVerticalGrid (
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(installedApps) { app ->

            val drawable = app.icon

            val bitmap = Bitmap.createBitmap(
                200,
                200,
                Bitmap.Config.ARGB_8888
            )
            //Text(text = drawable.intrinsicWidth.toString())
            //Text(text = w.toString())
            val canvas = Canvas(bitmap)
            drawable?.setBounds(0, 0, canvas.width, canvas.height)
            drawable?.draw(canvas)
            val imageBitmap = bitmap.asImageBitmap()
            Image(
                bitmap = imageBitmap,
                contentDescription = "Adaptive Icon"
            )


//
//            if (drawable == null) {
//                Text(text = "null")
//            }
//            Log.d("TYPE", drawable.toString())
//                when (drawable) {
//                    is BitmapDrawable -> {
//                        val bitmap = drawable.bitmap
//                        val imageBitmap = bitmap.asImageBitmap()
//                        Image(
//                            bitmap = imageBitmap,
//                            contentDescription = "App Icon"
//
//                        )
////                        Text(text = "BitmapDrawable")
//                    }
////                    is VectorDrawable -> {
////                        Text(text = "vec")
////                        //val drawable = ContextCompat.getDrawable(LocalContext.current, drawable)
////                        val bitmap = Bitmap.createBitmap(
////                            drawable!!.intrinsicWidth,
////                            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
////                        )
////                        val canvas = Canvas(bitmap)
////                        drawable.setBounds(0, 0, w, h)
////                        drawable.draw(canvas)
////                    }
//                    is AdaptiveIconDrawable -> {
////                        Text(text = "AdaptiveIconDrawable")
//                        val bitmap = Bitmap.createBitmap(
//                            200,
//                            200,
//                            Bitmap.Config.ARGB_8888
//                        )
//                        //Text(text = drawable.intrinsicWidth.toString())
//                        //Text(text = w.toString())
//                        val canvas = Canvas(bitmap)
//                        drawable.setBounds(0, 0, canvas.width, canvas.height)
//                        drawable.draw(canvas)
//                        val imageBitmap = bitmap.asImageBitmap()
//                        Image(
//                            bitmap = imageBitmap,
//                            contentDescription = "Adaptive Icon"
//                        )
//                    }
//
//                    else -> {
//                        Text(text = app.name)
//                    }
//                }
//
//

        }
    }



}


