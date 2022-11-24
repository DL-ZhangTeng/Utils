package com.zhangteng.utils

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * 获取当前屏幕截图，包含状态栏
 */
fun Activity?.snapShotWithStatusBar(): Bitmap? {
    this ?: return null
    val view = window.decorView
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()
    val bmp = view.drawingCache
    val width = getScreenWidth()
    val height = getScreenHeight()
    var bp: Bitmap? = null
    bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
    view.destroyDrawingCache()
    return bp
}

/**
 * 获取当前屏幕截图，不包含状态栏
 */
fun Activity?.snapShotWithoutStatusBar(): Bitmap? {
    this ?: return null
    val view = window.decorView
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()
    val bmp = view.drawingCache
    val frame = Rect()
    window.decorView.getWindowVisibleDisplayFrame(frame)
    val statusBarHeight = frame.top
    val width = getScreenWidth()
    val height = getScreenHeight()
    var bp: Bitmap? = null
    bp = Bitmap.createBitmap(
        bmp, 0, statusBarHeight, width, height
                - statusBarHeight
    )
    view.destroyDrawingCache()
    return bp
}

/**
 * 获取当View截图
 */
fun View?.snapShotView(): Bitmap? {
    if (this == null) return null
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val c = Canvas(bmp)
    c.drawColor(Color.WHITE)
    draw(c)
    return bmp
}

/**
 * 获得状态栏的高度
 */
fun Context?.getStatusHeight(): Int {
    var statusHeight = -1
    this ?: return statusHeight
    try {
        val clazz = Class.forName("com.android.internal.R\$dimen")
        val `object` = clazz.newInstance()
        val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
        statusHeight = resources.getDimensionPixelSize(height)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return statusHeight
}

/**
 * 获取Density
 */
fun Context?.getDensity(): Float {
    if (this == null) return -1f
    return resources.displayMetrics.density
}

/**
 * 获取DPI
 */
fun Context?.getDensityDpi(): Int {
    if (this == null) return -1
    return resources.displayMetrics.densityDpi
}

/**
 * 获取屏幕宽度
 */
fun Context?.getScreenWidth(): Int {
    if (this == null) return -1
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.x
}

/**
 * 获取屏幕高度
 */
fun Context?.getScreenHeight(): Int {
    if (this == null) return -1
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.y
}