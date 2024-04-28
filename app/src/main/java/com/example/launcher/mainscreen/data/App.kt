package com.example.launcher.mainscreen.data

import android.graphics.drawable.Drawable

data class App(
    val name: String,
    val packageName: String,
    val icon: Drawable?,
    var checked: Boolean
)