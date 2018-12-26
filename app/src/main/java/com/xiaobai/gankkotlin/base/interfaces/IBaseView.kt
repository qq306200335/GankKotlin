package com.xiaobai.gankkotlin.base.interfaces

import com.xiaobai.gankkotlin.interfaces.IToolBarBack

/**
 * 通用view
 *
 * @author baiyunfei on 2018/11/23
 */
interface IBaseView {

    /**
     * 初始化toolbar
     *
     * @param title  标题
     * @param isBack 是否显示返回按钮
     */
    fun initToolBar(title: String, isBack: Boolean)

    /**
     * 设置toolbar点击返回回调
     *
     * @param iToolBarBack 返回按钮响应
     */
    fun setToolBarBack(iToolBarBack: IToolBarBack)

    /**
     * 是否显示toolbar
     *
     * @param isShow 是否显示
     */
    fun showToolBar(isShow: Boolean)

    /**
     * 显示正在加载view
     */
    fun showLoading()

    /**
     * 关闭正在加载view
     */
    fun hideLoading()

    /**
     * 显示提示
     *
     * @param msg 提示信息内容
     */
    fun toast(msg: String)

    /**
     * 显示提示
     *
     * @param str 提示错误日志
     */
    fun log(str: String)

    /**
     * 显示请求错误提示
     */
    fun showError(error: String)
}
