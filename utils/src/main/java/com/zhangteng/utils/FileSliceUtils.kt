package com.zhangteng.utils

import android.os.Environment
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.RandomAccessFile

/**
 * description: 文件分片工具
 * author: Swing
 * date: 2022/11/7
 */
class FileSliceUtils {
    /**
     * description: 分片后的文件集合
     */
    val sliceFiles: ArrayList<File> = ArrayList()

    /**
     * description: 分片的文件夹路径
     */
    var fileCachePath =
        Environment.getExternalStorageDirectory().absolutePath + File.separator + "cache" + File.separator + "sliceFile"

    /**
     * 文件分割方法
     * @param targetFile 分割的文件
     * @param sliceSize 分割文件的大小
     * @return int 文件切割的个数
     */
    fun splitFile(targetFile: File, sliceSize: Long): Int {

        //计算切割文件大小
        val count =
            if (targetFile.length() % sliceSize == 0L) (targetFile.length() / sliceSize).toInt() else (targetFile.length() / sliceSize + 1).toInt()
        var raf: RandomAccessFile? = null
        try {
            //获取目标文件 预分配文件所占的空间 在磁盘中创建一个指定大小的文件   r 是只读
            raf = RandomAccessFile(targetFile, "r")
            val length = raf.length() //文件的总长度
            var offSet = 0L //初始化偏移量
            for (i in 0 until count - 1) { //最后一片单独处理
                val begin = offSet
                val end = (i + 1) * sliceSize
                offSet = writeFile(targetFile.absolutePath, i, begin, end)
            }
            if (length - offSet > 0) {
                writeFile(targetFile.absolutePath, count - 1, offSet, length)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                raf!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return count
    }

    /**
     * 指定文件每一份的边界，写入不同文件中
     * @param file 源文件地址
     * @param index 源文件的顺序标识
     * @param begin 开始指针的位置
     * @param end 结束指针的位置
     * @return long
     */
    fun writeFile(file: String, index: Int, begin: Long, end: Long): Long {
        var endPointer = 0L
        try {
            //申明文件切割后的文件磁盘
            val randomAccessFile = RandomAccessFile(File(file), "r")
            //定义一个可读，可写的文件并且后缀名为.tmp的二进制文件
            //判断文件夹是否存在,如果不存在则创建文件夹
            val dir = File(fileCachePath)
            //判断文件夹是否存在,如果不存在则创建文件夹
            if (!dir.exists()) {
                dir.mkdir()
            }

            //读取切片文件
            val mFile = File(
                fileCachePath + File.separator + "slice_file" + "_" + index + file.substring(
                    file.lastIndexOf(".")
                )
            )
            sliceFiles.add(mFile)
            //如果存在
            if (!mFile.exists()) {
                val out = RandomAccessFile(mFile, "rw")
                //申明具体每一文件的字节数组
                val b = ByteArray(1024)
                var n = 0
                //从指定位置读取文件字节流
                randomAccessFile.seek(begin)
                //判断文件流读取的边界
                while (randomAccessFile.read(b)
                        .also { n = it } != -1 && randomAccessFile.filePointer <= end
                ) {
                    //从指定每一份文件的范围，写入不同的文件
                    out.write(b, 0, n)
                }

                //定义当前读取文件的指针
                endPointer = randomAccessFile.filePointer
                //关闭输入流
                randomAccessFile.close()
                //关闭输出流
                out.close()
            } else {
                //不存在
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return endPointer - 1024
    }

    /**
     * description 删除分片后的文件缓存
     */
    fun deleteSliceFiles() {
        if (sliceFiles.size > 0) {
            sliceFiles.clear()
        }
        fileCachePath.deleteDir(true)
    }
}