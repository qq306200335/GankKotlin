package com.xiaobai.gankkotlin.http

import com.xiaobai.libutils.common.MyLog
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

/**
 * Created by WuXiaolong on 2016/9/22.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
abstract class ApiCallback<M> : DisposableObserver<M>() {

    abstract fun onSuccess(model: M)

    abstract fun onFailure(msg: String)

    abstract fun onFinish()

    override fun onError(e: Throwable) {
        e.printStackTrace()
        if (e is HttpException) {

            val code = e.code()
            var msg = e.message

            if (code == 504) {
                msg = "网络不给力"
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试"
            }
            MyLog.d("ApiCallback","code=$code")

            onFailure(msg!!)
        } else {
            onFailure(e.message!!)
        }
        onFinish()
    }

    override fun onNext(model: M) {
        onSuccess(model)
    }

    override fun onComplete() {
        onFinish()
    }
}
