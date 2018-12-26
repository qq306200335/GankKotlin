package com.xiaobai.gankkotlin.module

import com.xiaobai.gankkotlin.model.MainModel
import com.xiaobai.gankkotlin.base.interfaces.IBaseView

/**
 * Created by WuXiaolong on 2015/9/23.
 * 处理业务需要哪些方法
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
interface MainView : IBaseView {

    fun getDataSuccess(model: MainModel)

    fun getDataFail(msg: String)

}
