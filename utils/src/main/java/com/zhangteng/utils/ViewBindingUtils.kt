package com.zhangteng.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * description: ViewBinding自动绑定
 * author: Swing
 * date: 2023/1/6
 */
object ViewBindingUtils {
    /**
     * description 绑定Activity
     *              protected var binding: vb? = null
     *              override fun setContentView(layoutResID: Int) {
     *                  binding = ViewBindingUtils.bindActivity<vb>(layoutInflater)
     *                  super.setContentView(binding?.root ?: layoutInflater.inflate(layoutResID, null))
     *              }
     */
    fun <vb : ViewBinding?> bindActivity(layoutInflater: LayoutInflater): vb? {
        var binding: vb? = null
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            try {
                val clazz = type.actualTypeArguments[0] as Class<vb>
                val method = clazz.getMethod("inflate", LayoutInflater::class.java)
                binding = method.invoke(null, layoutInflater) as vb
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }
        return binding
    }

    /**
     * description 绑定Fragment
     *             protected var binding: vb? = null
     *             override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     *                  binding = ViewBindingUtils.bindFragment<vb>(layoutInflater)
     *                  super.onViewCreated(view, savedInstanceState)
     *             }
     */
    fun <vb : ViewBinding?> bindFragment(layoutInflater: LayoutInflater): vb? {
        var binding: vb? = null
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            try {
                val clazz = type.actualTypeArguments[0] as Class<vb>
                val method = clazz.getMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.javaPrimitiveType
                )
                binding = method.invoke(
                    null,
                    layoutInflater,
                    getValueByPropName("mContainer", this, Fragment::class.java),
                    false
                ) as vb
                setValueByPropName(
                    "mView",
                    this,
                    binding!!.root,
                    Fragment::class.java
                )
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }
        return binding
    }

    private fun setValueByPropName(tar: String, o: Any?, `val`: Any?, clazz: Class<*>?) {
        val field = getFiled(tar, clazz)
        field.isAccessible = true
        try {
            field[o] = `val`
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    private fun getValueByPropName(filedName: String, o: Any?, clazz: Class<*>?): Any? {
        val field = getFiled(filedName, clazz)
        field.isAccessible = true
        try {
            return field[o]
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getFiled(tar: String, clazz: Class<*>?): Field {
        var clazz = clazz
        var error: String? = null
        var field: Field? = null
        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(tar)
                error = null
                break
            } catch (e: Exception) {
                clazz = clazz.superclass
                error = e.message
            }
        }
        if (error != null || field == null) {
            throw RuntimeException("无法获取源字段:$tar")
        }
        return field
    }
}