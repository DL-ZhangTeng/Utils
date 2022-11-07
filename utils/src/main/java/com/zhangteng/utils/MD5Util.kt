package com.zhangteng.utils

import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MD5Util {
    /**
     * 32位MD5加密
     * @param content 原文
     * @return md5值
     */
    fun md5Decode32(content: String): String {
        val hash: ByteArray = try {
            MessageDigest.getInstance("MD5").digest(content.toByteArray(StandardCharsets.UTF_8))
        } catch (e: NoSuchAlgorithmException) {
            return ""
        }
        val hex = StringBuilder(hash.size * 2)
        for (b in hash) {
            if (b.toInt() and 0xFF < 0x10) {
                hex.append("0")
            }
            hex.append(Integer.toHexString(b.toInt() and 0xFF))
        }
        return hex.toString()
    }

    /**
     * 文件的md5值
     *
     * @param path 要加密的文件的路径
     * @return 文件的md5值
     */
    fun getFileMD5(path: String?): String {
        val sb = StringBuilder()
        try {
            val fis = FileInputStream(path)
            //获取MD5加密器
            val md = MessageDigest.getInstance("md5")
            //类似读取文件
            val bytes = ByteArray(10240) //一次读取写入10k
            var len: Int
            //从原目的地读取数据
            while (fis.read(bytes).also { len = it } != -1) {
                //把数据写到md加密器，类比fos.write(bytes, 0, len);
                md.update(bytes, 0, len)
            }
            //读完整个文件数据，并写到md加密器中
            val digest = md.digest()
            //遍历字节，把每个字节拼接起来
            for (b in digest) {
                //把每个字节转换成16进制数
                val d = b.toInt() and 0xff
                //把int类型数据转为16进制字符串表示
                var herString = Integer.toHexString(d)
                //如果只有一位，则在前面补0.让其也是两位
                if (herString.length == 1) {
                    //字节高4位为0
                    //拼接字符串，拼成两位表示
                    herString = "0$herString"
                }
                sb.append(herString)
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}