package com.xiaobai.libutils.common

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @author 来自网络 on 2015/9/15
 * 跟网络相关的工具类
 */
class NetUtils private constructor() {

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        /**
         * 判断网络是否连接
         *
         * @param context 上下文
         * @return 结果
         */
        fun isConnected(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo

            return if (networkInfo != null && networkInfo.isConnected) {
                networkInfo.detailedState == NetworkInfo.DetailedState.CONNECTED
            } else {
                false
            }
        }

        /**
         * 判断是否是wifi连接
         */
        fun isWifi(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo

            return if (networkInfo != null && networkInfo.isConnected) {
                networkInfo.type == ConnectivityManager.TYPE_WIFI
            } else {
                false
            }
        }

        /**
         * 判断是否是数据网络连接
         */
        fun isMobile(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo

            return if (networkInfo != null && networkInfo.isConnected) {
                networkInfo.type == ConnectivityManager.TYPE_MOBILE
            } else {
                false
            }
        }

        /**
         * 打开网络设置界面
         */
        fun openSetting(activity: Activity) {
            val intent = Intent("/")
            val cm = ComponentName(
                "com.android.settings",
                "com.android.settings.WirelessSettings"
            )
            intent.component = cm
            intent.action = "android.intent.action.VIEW"
            activity.startActivityForResult(intent, 0)
        }
    }
}