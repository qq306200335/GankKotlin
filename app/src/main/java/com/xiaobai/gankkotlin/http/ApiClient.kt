package com.xiaobai.gankkotlin.http

import com.xiaobai.gankkotlin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by WuXiaolong on 2016/3/24.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
object ApiClient {

    var mRetrofit: Retrofit? = null

    fun retrofit(): Retrofit? {
        if (mRetrofit == null) {
            val builder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor)
            }
            val okHttpClient = builder.build()
            mRetrofit = Retrofit.Builder()
                .baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return mRetrofit
    }
}
