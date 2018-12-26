package com.xiaobai.gankkotlin.base.presenter

import com.xiaobai.gankkotlin.app.MyApplication
import com.xiaobai.gankkotlin.base.interfaces.IBaseView
import com.xiaobai.libutils.cache.DiskLruCacheHelper
import com.xiaobai.libutils.common.CommonUtils

import java.io.IOException
import java.io.ObjectInputStream

/**
 * 通用Presenter
 *
 * @author baiyunfei on 2017/11/22
 */
abstract class BasePresenter<V : IBaseView> {

    /**
     * 绑定的view
     */
    /**
     * 获取连接的view
     */
    var iView: V? = null
        private set

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    val isViewAttached: Boolean
        get() = iView != null

    /**
     * 获取缓存
     */
    fun getCache(cachePath: String): Any? {

        var `object`: Any? = null
        try {
            DiskLruCacheHelper.openCache(MyApplication.getContext())
            val objectInputStream = ObjectInputStream(
                DiskLruCacheHelper.read(
                    CommonUtils.hashKeyForDisk(cachePath)
                )
            )
            `object` = objectInputStream.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return `object`
    }

    /**
     * 保存缓存
     */
    fun setCache(cachePath: String, `object`: Any) {

        //缓存最新数据
        DiskLruCacheHelper.openCache(MyApplication.getContext())
        try {
            DiskLruCacheHelper.write(`object`, CommonUtils.hashKeyForDisk(cachePath))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    abstract fun initModel()

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    fun attachView(mvpView: V) {
        this.iView = mvpView
        initModel()
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    fun detachView() {
        this.iView = null
    }
}
