package com.xiaobai.gankkotlin.base.interfaces

/**
 * 通用fragment接口
 * @author baiyunfei on 2018/11/28
 * email 306200335@qq.com
 */
interface IBaseFragment {

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
    fun showError(errorId: Int)
}
