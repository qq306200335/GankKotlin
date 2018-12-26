package com.xiaobai.gankkotlin.http

import com.xiaobai.gankkotlin.model.HomeModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by WuXiaolong on 2016/3/24.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
interface ApiStores {

    //获取某几日干货网站数据
    @GET("history/content/10/{retrofit}")
    fun getGankList(@Path("retrofit") retrofit: String): Observable<HomeModel>

    companion object {
        //baseUrl
        val API_SERVER_URL = "http://gank.io/api/"
    }
}
