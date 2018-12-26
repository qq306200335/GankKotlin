package com.xiaobai.gankkotlin.http

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by WuXiaolong on 2016/9/22.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */

abstract class RetrofitCallback<M> : Callback<M> {

    abstract fun onSuccess(model: M?)

    abstract fun onFailure(code: Int, msg: String)

    abstract fun onThrowable(t: Throwable)

    abstract fun onFinish()

    override fun onResponse(call: Call<M>, response: Response<M>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
            onFailure(response.code(), response.errorBody()!!.toString())
        }
        onFinish()
    }

    override fun onFailure(call: Call<M>, t: Throwable) {
        onThrowable(t)
        onFinish()
    }
}
