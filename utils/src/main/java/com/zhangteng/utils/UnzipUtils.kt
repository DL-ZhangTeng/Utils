package com.zhangteng.utils

import java.io.*
import java.util.zip.ZipInputStream

/**
 * description: 解压文件
 * author: Swing
 * date: 2022/11/8
 */
object UnzipUtils {
    /**
     * 解压压缩文件到指定目录
     *
     * @param zipPath         压缩文件
     * @param outputDirectory 解压后的文件夹
     */
    fun unzipFile(zipPath: String?, outputDirectory: String, unzipListener: UnzipListener?) {
        // 创建解压目标目录
        val dir = File(outputDirectory)
        // 如果目标目录不存在，则创建
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                unzipListener?.onUnzip(dir, false)
                return
            }
        }
        var unzipFile: File? = null
        val inputStream: InputStream
        val zipInputStream: ZipInputStream
        // 打开压缩文件
        try {
            inputStream = FileInputStream(zipPath)
            zipInputStream = ZipInputStream(inputStream)

            // 读取一个进入点
            var zipEntry = zipInputStream.nextEntry
            // 使用1Mbuffer
            val buffer = ByteArray(1024 * 1024)
            // 解压时字节计数
            var count = 0
            // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
            while (zipEntry != null) {
                //如果是一个文件
                if (!zipEntry.isDirectory) {
                    // 如果是文件
                    var fileName = zipEntry.name
                    //截取文件的名字 去掉原文件夹名字
                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1)
                    //放到新的解压的文件路径
                    unzipFile = File(outputDirectory + File.separator + fileName)
                    if (unzipFile.createNewFile()) {
                        val fileOutputStream = FileOutputStream(unzipFile)
                        while (zipInputStream.read(buffer).also { count = it } > 0) {
                            fileOutputStream.write(buffer, 0, count)
                        }
                        fileOutputStream.close()
                    }
                }
                // 定位到下一个文件入口
                zipEntry = zipInputStream.nextEntry
            }
            unzipListener?.onUnzip(dir, true)
            inputStream.close()
            zipInputStream.close()
        } catch (ignore: IOException) {
            unzipListener?.onUnzip(dir, false)
        }
    }

    interface UnzipListener {
        fun onUnzip(unzip: File?, isUnzip: Boolean)
    }
}