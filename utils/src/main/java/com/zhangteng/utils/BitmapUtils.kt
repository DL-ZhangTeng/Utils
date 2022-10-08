package com.zhangteng.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt


/**
 * description drawable转Bitmap
 * @param
 * @return Bitmap
 */
fun Drawable?.drawableToBitmap(): Bitmap? {
    if (this == null) return null
    val bitmap = Bitmap.createBitmap(
        intrinsicWidth,
        intrinsicHeight,
        if (opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
    )
    val canvas = Canvas(bitmap)
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    draw(canvas)
    return bitmap
}

/**
 * description 保存bitmap
 * @param dir 文件路径
 * @param name 文件名
 * @return 保存是否成功
 */
fun Bitmap?.saveBitmap(dir: String?, name: String?): Boolean {
    if (this == null) return false
    val path = File(dir ?: File.separator)
    if (!path.exists()) {
        path.mkdirs()
    }
    val file = File("$path${File.separator}$name")
    if (file.exists()) {
        file.delete()
    }
    if (!file.exists()) {
        try {
            file.createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    } else {
        return true
    }
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = FileOutputStream(file)
        compress(
            Bitmap.CompressFormat.PNG, 100,
            fileOutputStream
        )
        fileOutputStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    } finally {
        try {
            fileOutputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return true
}

/**
 * description 通过字节码创建bitmap
 * @param maxNumOfPixels 最大像素
 * @return bitmap
 */
fun ByteArray.makeBitmap(maxNumOfPixels: Int): Bitmap? {
    return try {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(
            this, 0, size,
            options
        )
        if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
            return null
        }
        options.inSampleSize = options.computeSampleSize(-1, maxNumOfPixels)
        options.inJustDecodeBounds = false
        options.inDither = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        BitmapFactory.decodeByteArray(
            this, 0, size,
            options
        )
    } catch (ex: NullPointerException) {
        null
    } catch (ex: OutOfMemoryError) {
        null
    }
}

/**
 * description 计算样品大小
 * @param minSideLength 最小像素
 * @param maxNumOfPixels 最大像素
 * @return
 */
fun BitmapFactory.Options.computeSampleSize(minSideLength: Int, maxNumOfPixels: Int): Int {
    val initialSize = computeInitialSampleSize(minSideLength, maxNumOfPixels)
    var roundedSize: Int
    if (initialSize <= 8) {
        roundedSize = 1
        while (roundedSize < initialSize) {
            roundedSize = roundedSize shl 1
        }
    } else {
        roundedSize = (initialSize + 7) / 8 * 8
    }
    return roundedSize
}

/**
 * description 计算样品大小
 * @param minSideLength 最小像素
 * @param maxNumOfPixels 最大像素
 * @return
 */
fun BitmapFactory.Options.computeInitialSampleSize(minSideLength: Int, maxNumOfPixels: Int): Int {
    val w = outWidth.toDouble()
    val h = outHeight.toDouble()
    val lowerBound = if (maxNumOfPixels < 0) 1 else ceil(sqrt(w * h / maxNumOfPixels)).toInt()
    val upperBound = if (minSideLength < 0) 128 else min(
        floor(w / minSideLength),
        floor(h / minSideLength)
    ).toInt()
    if (upperBound < lowerBound) {
        // return the larger one when there is no overlapping zone.
        return lowerBound
    }
    return if (maxNumOfPixels < 0 && minSideLength < 0) {
        1
    } else if (minSideLength < 0) {
        lowerBound
    } else {
        upperBound
    }
}

/**
 * 设置水印图片在左上角
 *
 * @param context     上下文
 * @param watermark
 * @param paddingLeft
 * @param paddingTop
 * @return
 */
fun Bitmap?.createWaterMaskLeftTop(
    context: Context?,
    watermark: Bitmap?,
    paddingLeft: Int,
    paddingTop: Int
): Bitmap? {
    if (this == null) return null
    return if (watermark == null) this else watermark.createWaterMaskBitmap(
        this,
        context.dp2px(paddingLeft.toFloat()),
        context.dp2px(paddingTop.toFloat())
    )
}

/**
 * 设置水印图片在右下角
 *
 * @param context       上下文
 * @param watermark
 * @param paddingRight
 * @param paddingBottom
 * @return
 */
fun Bitmap?.createWaterMaskRightBottom(
    context: Context?,
    watermark: Bitmap?,
    paddingRight: Int,
    paddingBottom: Int
): Bitmap? {
    if (this == null) return null
    return if (watermark == null) this else createWaterMaskBitmap(
        watermark,
        width - watermark.width - context.dp2px(paddingRight.toFloat()),
        height - watermark.height - context.dp2px(paddingBottom.toFloat())
    )
}

/**
 * 设置水印图片到右上角
 *
 * @param context
 * @param watermark
 * @param paddingRight
 * @param paddingTop
 * @return
 */
fun Bitmap?.createWaterMaskRightTop(
    context: Context?,
    watermark: Bitmap?,
    paddingRight: Int,
    paddingTop: Int
): Bitmap? {
    if (this == null) return null
    return if (watermark == null) this else createWaterMaskBitmap(
        watermark,
        width - watermark.width - context.dp2px(paddingRight.toFloat()),
        context.dp2px(paddingTop.toFloat())
    )
}

/**
 * 设置水印图片到左下角
 *
 * @param context
 * @param watermark
 * @param paddingLeft
 * @param paddingBottom
 * @return
 */
fun Bitmap?.createWaterMaskLeftBottom(
    context: Context?,
    watermark: Bitmap?,
    paddingLeft: Int,
    paddingBottom: Int
): Bitmap? {
    if (this == null) return null
    return if (watermark == null) this else createWaterMaskBitmap(
        watermark, context.dp2px(paddingLeft.toFloat()),
        height - watermark.height - context.dp2px(paddingBottom.toFloat())
    )
}

/**
 * 设置水印图片到中下角
 *
 * @param context
 * @param watermark
 * @return
 */
fun Bitmap?.createWaterMaskCenterBottom(context: Context?, watermark: Bitmap?): Bitmap? {
    if (this == null) return null
    return if (watermark == null) this else createWaterMaskBitmap(
        watermark, 0,
        height * 2 / 3
    )
}

/**
 * 设置水印图片到中间
 *
 * @param watermark
 * @return
 */
fun Bitmap?.createWaterMaskCenter(watermark: Bitmap?): Bitmap? {
    if (this == null) return null
    return if (watermark == null) this else createWaterMaskBitmap(
        watermark,
        (width - watermark.width) / 2,
        (height - watermark.height) / 2
    )
}

/**
 * description 添加水印
 * @param watermark 水印图片
 * @param paddingLeft 距左px
 * @param paddingTop 距顶px
 * @return
 */
fun Bitmap?.createWaterMaskBitmap(
    watermark: Bitmap?,
    paddingLeft: Int = 0,
    paddingTop: Int = 0
): Bitmap? {
    if (this == null) return null
    if (watermark == null) return this
    val width = width
    val height = height
    val bitmapTwo = watermark.scaleWithWH(width.toDouble(), (height / 3).toDouble())
    //创建一个bitmap
    val newBitmap =
        Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // 创建一个新的和SRC长度宽度一样的位图
    //将该图片作为画布
    val canvas = Canvas(newBitmap)
    //在画布 0，0坐标上开始绘制原始图片
    canvas.drawBitmap(this, 0f, 0f, null)
    //在画布上绘制水印图片
    if (bitmapTwo != null) {
        canvas.drawBitmap(bitmapTwo, paddingLeft.toFloat(), paddingTop.toFloat(), null)
    }
    // 保存
    canvas.save()
    // 存储
    canvas.restore()
    return newBitmap
}

/**
 * 给图片添加文字到左上角
 *
 * @param context
 * @param text
 * @return
 */
fun Bitmap?.drawTextToLeftTop(
    context: Context?,
    text: String?,
    size: Int,
    color: Int,
    paddingLeft: Int,
    paddingTop: Int
): Bitmap? {
    if (this == null || text == null) return null
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = color
    paint.textSize = context.dp2px(size.toFloat()).toFloat()
    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)
    return drawTextToBitmap(
        text,
        paint,
        context.dp2px(paddingLeft.toFloat()),
        context.dp2px(paddingTop.toFloat()) + bounds.height()
    )
}

/**
 * 绘制文字到右下角
 *
 * @param context
 * @param text
 * @param size
 * @param color
 * @return
 */
fun Bitmap?.drawTextToRightBottom(
    context: Context?,
    text: String?,
    size: Int,
    color: Int,
    paddingRight: Int,
    paddingBottom: Int
): Bitmap? {
    if (this == null || text == null) return null
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = color
    paint.textSize = context.dp2px(size.toFloat()).toFloat()
    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)
    return drawTextToBitmap(
        text,
        paint,
        width - bounds.width() - context.dp2px(paddingRight.toFloat()),
        height - context.dp2px(paddingBottom.toFloat())
    )
}

/**
 * 绘制文字到右上方
 *
 * @param context
 * @param text
 * @param size
 * @param color
 * @param paddingRight
 * @param paddingTop
 * @return
 */
fun Bitmap?.drawTextToRightTop(
    context: Context?,
    text: String?,
    size: Int,
    color: Int,
    paddingRight: Int,
    paddingTop: Int
): Bitmap? {
    if (this == null || text == null) return null
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = color
    paint.textSize = context.dp2px(size.toFloat()).toFloat()
    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)
    return drawTextToBitmap(
        text,
        paint,
        width - bounds.width() - context.dp2px(paddingRight.toFloat()),
        context.dp2px(paddingTop.toFloat()) + bounds.height()
    )
}

/**
 * 绘制文字到左下方
 *
 * @param context
 * @param text
 * @param size
 * @param color
 * @param paddingLeft
 * @param paddingBottom
 * @return
 */
fun Bitmap?.drawTextToLeftBottom(
    context: Context?,
    text: String?,
    size: Int,
    color: Int,
    paddingLeft: Int,
    paddingBottom: Int
): Bitmap? {
    if (this == null || text == null) return null
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = color
    paint.textSize = context.dp2px(size.toFloat()).toFloat()
    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)
    return drawTextToBitmap(
        text,
        paint,
        context.dp2px(paddingLeft.toFloat()),
        height - context.dp2px(paddingBottom.toFloat())
    )
}

/**
 * 绘制文字到中间
 *
 * @param context
 * @param text
 * @param size
 * @param color
 * @return
 */
fun Bitmap?.drawTextToCenter(
    context: Context?,
    text: String?,
    size: Int,
    color: Int
): Bitmap? {
    if (this == null || text == null) return null
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = color
    paint.textSize = context.dp2px(size.toFloat()).toFloat()
    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)
    return drawTextToBitmap(
        text,
        paint,
        (width - bounds.width()) / 2,
        (height + bounds.height()) / 2
    )
}

/**
 * description 图片上绘制文字
 * @param
 * @return
 */
fun Bitmap?.drawTextToBitmap(
    text: String?,
    paint: Paint?,
    paddingLeft: Int,
    paddingTop: Int
): Bitmap? {
    if (this == null || text == null || paint == null) return null
    var bitmap: Bitmap = this
    var bitmapConfig = bitmap.config
    paint.isDither = true // 获取跟清晰的图像采样
    paint.isFilterBitmap = true // 过滤一些
    if (bitmapConfig == null) {
        bitmapConfig = Bitmap.Config.ARGB_8888
    }
    bitmap = bitmap.copy(bitmapConfig, true)
    val canvas = Canvas(bitmap)
    canvas.drawText(text, paddingLeft.toFloat(), paddingTop.toFloat(), paint)
    return bitmap
}

/**
 * 缩放图片
 *
 * @param w
 * @param h
 * @return
 */
fun Bitmap?.scaleWithWH(w: Double, h: Double): Bitmap? {
    return if (w == 0.0 || h == 0.0 || this == null) {
        this
    } else {
        // 计算缩放比例
        val scaleWidth = (w / width).toFloat()
        val scaleHeight = (h / height).toFloat()
        // 创建一个matrix容器
        val matrix = Matrix()
        // 开始缩放
        matrix.postScale(scaleWidth, scaleHeight)
        // 创建缩放后的图片
        Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }
}

/**
 * 质量压缩（压缩到size以下）
 *
 * @param size 压缩后的大小，默认100KB
 * @return
 */
fun Bitmap?.compressImage(size: Long = 100 * 1024): Bitmap? {
    return if (this == null) {
        null
    } else {
        var byteOutputStream: ByteArrayOutputStream? = null
        var isBm: ByteArrayInputStream? = null
        try {
            byteOutputStream = ByteArrayOutputStream()
            // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到byteOutputStream中
            this.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
            var options = 90
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            while (byteOutputStream.toByteArray().size > size) {
                // 重置byteOutputStream即清空byteOutputStream
                byteOutputStream.reset()
                // 这里压缩options%，把压缩后的数据存放到byteOutputStream中
                this.compress(Bitmap.CompressFormat.JPEG, options, byteOutputStream)
                // 每次都减少10
                options -= 10
            }
            // 把压缩后的数据byteOutputStream存放到ByteArrayInputStream中
            isBm = ByteArrayInputStream(byteOutputStream.toByteArray())
            return BitmapFactory.decodeStream(isBm, null, null)
        } catch (e: Exception) {
            this
        } finally {
            byteOutputStream?.close()
            isBm?.close()
        }
    }
}

/**
 * 比例压缩（尺寸压缩、采样率压缩）
 * @param mInSampleSize 目标采样率，默认1
 * @param mInPreferredConfig 目标颜色和透明度，默认Bitmap.Config.RGB_565
 * @return
 */
fun Bitmap?.compressImage(
    mInSampleSize: Int = 1,
    mInPreferredConfig: Bitmap.Config = Bitmap.Config.RGB_565
): Bitmap? {
    return if (this == null) {
        null
    } else {
        var byteOutputStream: ByteArrayOutputStream? = null
        var isBm: ByteArrayInputStream? = null
        try {
            val newOpts = BitmapFactory.Options()
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true

            if (mInSampleSize != 1) {//如果设置了mInSampleSize
                newOpts.inSampleSize = mInSampleSize // 设置缩放比例
            } else {//如果未设置了mInSampleSize计算缩放比
                val w = newOpts.outWidth
                val h = newOpts.outHeight
                val mWidth = 720
                val mHeight = 1280
                // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                // be=1表示不缩放
                var be = 1
                if (w > h && w > mWidth) { // 如果宽度大的话根据宽度固定大小缩放
                    be = newOpts.outWidth / mWidth
                } else if (w < h && h > mHeight) { // 如果高度高的话根据高度固定大小缩放
                    be = newOpts.outHeight / mHeight
                }
                if (be <= 0) be = 1
                newOpts.inSampleSize = be // 设置缩放比例
            }

            //降低图片从ARGB888到RGB565
            newOpts.inPreferredConfig = mInPreferredConfig

            byteOutputStream = ByteArrayOutputStream()
            compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            isBm = ByteArrayInputStream(byteOutputStream.toByteArray())
            return BitmapFactory.decodeStream(isBm, null, newOpts)
        } catch (e: Exception) {
            this
        } finally {
            byteOutputStream?.close()
            isBm?.close()
        }
    }
}