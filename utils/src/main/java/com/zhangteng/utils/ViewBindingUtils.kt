package com.zhangteng.utils

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
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
     * description 绑定Activity 必须保证被绑定类第一个泛型是ViewBinding
     *              protected var binding: vb? = null
     *              override fun setContentView(layoutResID: Int) {
     *                  binding = ViewBindingUtils.inflate<vb>(this)
     *                  super.setContentView(binding?.root ?: layoutInflater.inflate(layoutResID, null))
     *              }
     *
     * @param activity 绑定Activity 必须保证被绑定类第一个泛型是ViewBinding
     */
    fun <vb : ViewBinding?> inflate(activity: Activity): vb? {
        var binding: vb? = null
        val type = activity.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            try {
                val clazz = type.actualTypeArguments[0] as Class<vb>
                val method = clazz.getMethod("inflate", LayoutInflater::class.java)
                binding = method.invoke(null, activity.layoutInflater) as vb
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
     * description 绑定Activity 必须保证被绑定类第一个泛型是ViewBinding
     *              protected var binding: vb? = null
     *              override fun setContentView(view: View?) {
     *                  binding = ViewBindingUtils.bind<vb>(this, view)
     *                  super.setContentView(binding?.root ?: view)
     *              }
     *
     * @param activity 绑定Activity 必须保证被绑定类第一个泛型是ViewBinding
     * @param view 被绑定的view
     */
    fun <vb : ViewBinding?> bind(activity: Activity, view: View): vb? {
        var binding: vb? = null
        val type = activity.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            try {
                val clazz = type.actualTypeArguments[0] as Class<vb>
                val method = clazz.getMethod("bind", View::class.java)
                binding = method.invoke(null, view) as vb
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
     * description 绑定Fragment 必须保证被绑定类第一个泛型是ViewBinding
     *             protected var binding: vb? = null
     *             override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     *                  binding = ViewBindingUtils.inflate<vb>(this)
     *                  super.onViewCreated(binding?.root ?: view, savedInstanceState)
     *             }
     *
     * @param fragment 绑定Fragment 必须保证被绑定类第一个泛型是ViewBinding
     */
    fun <vb : ViewBinding?> inflate(fragment: Fragment): vb? {
        var binding: vb? = null
        val type = fragment.javaClass.genericSuperclass
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
                    fragment.layoutInflater,
                    getValueByPropName("mContainer", fragment, Fragment::class.java),
                    false
                ) as vb
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
     * description 绑定Fragment 必须保证被绑定类第一个泛型是ViewBinding
     *             protected var binding: vb? = null
     *             override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     *                  binding = ViewBindingUtils.bind<vb>(this, view)
     *                  super.onViewCreated(binding?.root ?: view, savedInstanceState)
     *             }
     *
     * @param fragment 绑定Fragment 必须保证被绑定类第一个泛型是ViewBinding
     * @param view 被绑定的view
     */
    fun <vb : ViewBinding?> bind(fragment: Fragment, view: View): vb? {
        var binding: vb? = null
        val type = fragment.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            try {
                val clazz = type.actualTypeArguments[0] as Class<vb>
                val method = clazz.getMethod("bind", View::class.java)
                binding = method.invoke(null, view) as vb
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
     * description 绑定任意View
     *             protected var binding: vb? = null
     *             override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     *                  binding = ViewBindingUtils.inflate<vb>(layoutInflater, root, false)
     *                  super.onViewCreated(binding?.root ?: view, savedInstanceState)
     *             }
     *
     * @param layoutInflater 布局渲染类
     * @param parent 父控件
     * @param attachToParent 是否绑定到父控件
     */
    inline fun <reified vb : ViewBinding?> inflate(
        layoutInflater: LayoutInflater,
        parent: View,
        attachToParent: Boolean
    ): vb? {
        var binding: vb? = null
        try {
            val method = vb::class.java.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            )
            binding = method.invoke(
                null,
                layoutInflater,
                parent,
                attachToParent
            ) as vb
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return binding
    }

    /**
     * description 绑定任意View
     *             protected var binding: vb? = null
     *             override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     *                  binding = ViewBindingUtils.inflate<vb>(layoutInflater)
     *                  super.onViewCreated(binding?.root ?: view, savedInstanceState)
     *             }
     *
     * @param layoutInflater 布局渲染类
     */
    inline fun <reified vb : ViewBinding?> inflate(layoutInflater: LayoutInflater): vb? {
        var binding: vb? = null
        try {
            val method = vb::class.java.getMethod("inflate", LayoutInflater::class.java)
            binding = method.invoke(null, layoutInflater) as vb
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return binding
    }

    /**
     * description 绑定任意View
     *             protected var binding: vb? = null
     *             override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     *                  binding = ViewBindingUtils.bind<vb>(view)
     *                  super.onViewCreated(binding?.root ?: view, savedInstanceState)
     *             }
     *
     * @param view 被绑定的view
     */
    inline fun <reified vb : ViewBinding?> bind(view: View): vb? {
        var binding: vb? = null
        try {
            val method = vb::class.java.getMethod("bind", View::class.java)
            binding = method.invoke(null, view) as vb
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return binding
    }

    fun setValueByPropName(filedName: String, obj: Any?, subObj: Any?, clazz: Class<*>?) {
        val field = getFiled(filedName, clazz)
        field.isAccessible = true
        try {
            field[obj] = subObj
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun getValueByPropName(filedName: String, obj: Any?, clazz: Class<*>?): Any? {
        val field = getFiled(filedName, clazz)
        field.isAccessible = true
        try {
            return field[obj]
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }

    fun getFiled(tar: String, clazz: Class<*>?): Field {
        var localClazz = clazz
        var error: String? = null
        var field: Field? = null
        while (localClazz != null) {
            try {
                field = localClazz.getDeclaredField(tar)
                error = null
                break
            } catch (e: Exception) {
                localClazz = localClazz.superclass
                error = e.message
            }
        }
        if (error != null || field == null) {
            throw RuntimeException("无法获取源字段:$tar")
        }
        return field
    }
}