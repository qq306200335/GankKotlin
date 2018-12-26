package com.xiaobai.gankkotlin.app

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
class AppManager private constructor() {

    val isNullActivityStack: Boolean
        get() = activityStack == null

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity {
        return activityStack!!.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
            }
        }
    }

    /**
     * 结束置顶Activity上面的所以Activity
     */
    fun finishActivityTop(cls: Class<*>) {
        //栈为空的话
        if (activityStack == null) {
            return
        }

        for (i in activityStack!!.size - 1 downTo 1) {
            if (null != activityStack!![i]) {
                //到达指定的Activity，停止清除
                if (activityStack!![i].javaClass == cls) {
                    return
                }
                finishActivity(activityStack!![i])
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        //栈为空的话
        if (activityStack == null) {
            return
        }

        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                activityStack!![i].finish()
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            finishAllActivity()
            val activityMgr = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityMgr.restartPackage(context.packageName)
            System.exit(0)
        } catch (e: Exception) {
        }

    }

    companion object {

        private var activityStack: Stack<Activity>? = null
        private var instance: AppManager? = null

        /**
         * 单一实例
         */
        val appManager: AppManager
            get() {
                if (instance == null) {
                    instance = AppManager()
                }
                return instance!!
            }
    }
}
