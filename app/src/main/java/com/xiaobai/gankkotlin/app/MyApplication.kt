package com.xiaobai.gankkotlin.app

import android.app.Application
import android.content.Context
import com.xiaobai.libutils.common.MyLog

/**
 * @author baiyunfei on 2018/11/23
 * email 306200335@qq.com
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this

        MyLog.initLog()

//        MyLog.close()
    }

    companion object {

        private var mContext: Context? = null

        /**
         * @return 全局的上下文
         */
        fun getContext(): Context {
            return mContext!!
        }
    }
}