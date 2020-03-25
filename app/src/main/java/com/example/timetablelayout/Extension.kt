package com.example.timetablelayout

import android.content.res.Resources

val Int.dp get() = (Resources.getSystem().displayMetrics.density * this).toInt()