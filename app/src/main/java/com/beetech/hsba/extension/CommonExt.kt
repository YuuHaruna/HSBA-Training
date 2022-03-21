package com.beetech.hsba.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*

import androidx.core.content.ContextCompat

fun Collection<*>?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}

@ColorInt
fun Context.color(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Context.drawable(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context.string(@StringRes res: Int): String {
    return getString(res)
}

fun Context.inflate(@LayoutRes res: Int, parent: ViewGroup, attachView: Boolean = true) : View {
    return LayoutInflater.from(this).inflate(res, parent, attachView)
}

fun Context.statusBarHeight(restrictToLollipop: Boolean = true): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0 && (!restrictToLollipop || (restrictToLollipop && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP))) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

