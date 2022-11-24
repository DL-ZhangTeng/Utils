package com.zhangteng.utils

import com.google.gson.Gson
import java.io.*
import java.nio.charset.StandardCharsets

/**
 * description: 本地json文件解析
 * author: Swing
 * date: 2022/11/10
 */
object JsonUtils {
    /**
     * description 读取本地json
     *
     * @param file json文件
     * @return 类
     */
    fun <T> readJson(file: File?, clazz: Class<T>): T? {
        return try {
            if (file == null) {
                return null
            }
            if (!file.exists()) {
                return null
            }
            val fileInputStream = FileInputStream(file.absolutePath)
            //InputStreamReader 将字节输入流转换为字符流
            val isr = InputStreamReader(fileInputStream, StandardCharsets.UTF_8)
            //包装字符流,将字符流放入缓存里
            val br = BufferedReader(isr)
            var line: String?
            //StringBuilder和StringBuffer功能类似,存储字符串
            val builder = StringBuilder()
            while (br.readLine().also { line = it } != null) {
                //append 被选元素的结尾(仍然在内部)插入指定内容,缓存的内容依次存放到builder中
                builder.append(line)
            }
            br.close()
            isr.close()
            fileInputStream.close()
            Gson().fromJson(builder.toString(), clazz)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}