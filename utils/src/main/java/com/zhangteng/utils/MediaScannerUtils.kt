package com.zhangteng.utils

import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import java.io.File

/**
 * description: 媒体刷新
 * author: Swing
 * date: 2023/2/18
 */
object MediaScannerUtils {
    /**
     * 插入相册
     *
     * @param context
     * @param filePath
     * @return
     */
    fun insertMedia(
        context: Context,
        filePath: String?,
        scanListener: MediaScannerListener
    ): Boolean {
        if (TextUtils.isEmpty(filePath)) return false
        val file = File(filePath!!)
        //保存图片后发送广播通知更新数据库
        val uri = Uri.fromFile(file)
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
        return try {
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                file.absolutePath,
                file.name,
                null
            )
            MediaScannerConnectionClient(context.applicationContext, file.absolutePath).scanMedia()
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

    class MediaScannerConnectionClient : MediaScannerConnection.MediaScannerConnectionClient {
        private var mMs: MediaScannerConnection? = null
        private var mPath: String
        private var mListener: MediaScannerListener? = null

        constructor(context: Context, path: String, listener: MediaScannerListener?) {
            mListener = listener
            mPath = path
            mMs = MediaScannerConnection(context.applicationContext, this)
        }

        constructor(context: Context, path: String) {
            mPath = path
            mMs = MediaScannerConnection(context.applicationContext, this)
        }

        override fun onMediaScannerConnected() {
            mListener?.onMediaScannerConnected(mMs, mPath)
        }

        override fun onScanCompleted(path: String, uri: Uri) {
            mListener?.onScanCompleted(mMs, path, uri)
        }

        fun scanMedia() {
            mMs?.connect()
        }
    }

    interface MediaScannerListener {
        fun onMediaScannerConnected(mMs: MediaScannerConnection?, path: String?) {
            path?.let {
                mMs?.scanFile(path, null)
            }
        }

        fun onScanCompleted(mMs: MediaScannerConnection?, path: String, uri: Uri) {
            mMs?.disconnect()
        }
    }
}