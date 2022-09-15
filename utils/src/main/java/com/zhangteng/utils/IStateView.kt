package com.zhangteng.utils

import android.content.DialogInterface
import android.view.View

/**
 * description: StateView加载中显示抽象接口
 * author: Swing
 * date: 2022/9/14
 */
interface ILoadingView {
    /**
     * description 加载中弹窗
     * @param mLoadingText 加载中...
     */
    fun showProgressDialog(mLoadingText: String? = StateViewHelper.loadingText)

    /**
     * description 关闭加载中弹窗
     */
    fun dismissProgressDialog()
}

/**
 * description: StateView显示抽象接口
 * author: Swing
 * date: 2022/9/14
 */
interface IStateView : ILoadingView {
    /**
     * description 创建 StateViewHelper类，并回调重试请求、取消请求监听
     */
    fun createStateViewHelper(): StateViewHelper

    /**
     * description 无网络视图
     * @param contentView 被替换的View
     */
    fun showNoNetView(contentView: View?)

    /**
     * description 超时视图
     * @param contentView 被替换的View
     */
    fun showTimeOutView(contentView: View?)

    /**
     * description 无数据视图
     * @param contentView 被替换的View
     */
    fun showEmptyView(contentView: View?)

    /**
     * description 错误视图
     * @param contentView 被替换的View
     */
    fun showErrorView(contentView: View?)

    /**
     * description 未登录视图
     * @param contentView 被替换的View
     */
    fun showNoLoginView(contentView: View?)

    /**
     * description 业务视图
     * @param contentView 要展示的View
     */
    fun showContentView(contentView: View?)

    /**
     * description 状态View重新请求回调
     * @param view 重试按钮
     */
    fun againRequestByStateViewHelper(view: View)

    /**
     * description 加载中取消回调
     * @param dialog 加载中弹窗
     */
    fun cancelRequestByStateViewHelper(dialog: DialogInterface)
}