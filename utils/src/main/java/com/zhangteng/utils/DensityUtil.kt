package com.zhangteng.utils

import android.content.Context
import android.util.TypedValue

/**
 * dp转px
 *
 * @param dpVal
 */
fun Context?.dp2px(dpVal: Float): Int {
    return if (this != null) {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal,
            resources.displayMetrics
        ).toInt()
    } else -1
}

/**
 * sp转px
 *
 * @param spVal
 */
fun Context?.sp2px(spVal: Float): Int {
    return if (this != null) {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal,
            resources.displayMetrics
        ).toInt()
    } else -1
}

/**
 * px转dp
 *
 * @param pxVal
 */
fun Context?.px2dp(pxVal: Float): Float {
    if (this == null) return -1f
    val scale = resources.displayMetrics.density
    return pxVal / scale
}

/**
 * px转sp
 *
 * @param pxVal
 */
fun Context?.px2sp(pxVal: Float): Float {
    if (this == null) return -1f
    return pxVal / resources.displayMetrics.scaledDensity
}