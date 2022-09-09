package com.zhangteng.utils

/**
 * description: 网络响应接口
 * author: Swing
 * date: 2022/9/9
 */
interface IResponse<T> {

    fun isSuccess(): Boolean

    fun getResult(): T

    fun getCode(): Int

    fun getMsg(): String

}