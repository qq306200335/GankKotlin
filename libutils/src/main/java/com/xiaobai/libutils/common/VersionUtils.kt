package com.xiaobai.libutils.common

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * @author baiyunfei on 2015/9/18
 * email 306200335@qq.com
 */
object VersionUtils {

    private fun getPackageInfo(context: Context): PackageInfo? {

        val packageInfo: PackageInfo

        try {
            val packageManager = context.packageManager
            packageInfo = packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_CONFIGURATIONS
            )

            return packageInfo
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 获取应用的版本名
     */
    fun getVersionName(context: Context): String {
        return getPackageInfo(context)!!.versionName
    }

    /**
     * 获取应用的版本号
     */
    fun getVersionCode(context: Context): Int {
        return getPackageInfo(context)!!.versionCode
    }
}
