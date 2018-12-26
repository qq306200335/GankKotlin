package com.xiaobai.libutils.common

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

/**
 * 获取屏幕宽度
 *
 * @author 来自网络 on 2015/7/14
 */
class ScreenUtils private constructor() {

    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        /**
         * 判断设备是否是平板
         *
         * @param context 上下文
         * @return 是否是大屏
         */
        fun isTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }

        /**
         * 获得屏幕像素密度dpi
         *
         * @param context 上下文
         * @return 屏幕像素密度dpi
         */
        fun getDpi(context: Context): String {
            val xDpi = context.resources.displayMetrics.densityDpi.toFloat()
            return xDpi.toString() + ""
        }

        /**
         * 获得屏幕高度
         *
         * @param context 上下文
         * @return 屏幕高度
         */
        fun getScreenWidth(context: Context): Int {
            val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

        /**
         * 获得屏幕宽度
         *
         * @param context 上下文
         * @return 屏幕宽度
         */
        fun getScreenHeight(context: Context): Int {
            val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }

        /**
         * 获得状态栏的高度
         *
         * @param context 上下文
         * @return 状态栏的高度
         */
        fun getStatusHeight(context: Context): Int {

            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = Integer.parseInt(
                    clazz.getField("status_bar_height")
                        .get(`object`).toString()
                )
                statusHeight = context.resources.getDimensionPixelSize(height)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return statusHeight
        }

        /**
         * 获取当前屏幕截图，包含状态栏
         *
         * @param activity 上下文
         * @return 当前屏幕截图，包含状态栏
         */
        fun snapShotWithStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val width = getScreenWidth(activity)
            val height = getScreenHeight(activity)
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
            view.destroyDrawingCache()
            return bp

        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         *
         * @param activity 上下文
         * @return 当前屏幕截图，不包含状态栏
         */
        fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val frame = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(frame)
            val statusBarHeight = frame.top

            val width = getScreenWidth(activity)
            val height = getScreenHeight(activity)
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
            view.destroyDrawingCache()
            return bp
        }

        /**
         * 获取屏幕中控件顶部位置的高度--即控件顶部的Y点
         *
         * @return 控件顶部的Y点
         */
        fun getScreenViewTopHeight(view: View): Int {
            return view.top
        }

        /**
         * 获取屏幕中控件底部位置的高度--即控件底部的Y点
         *
         * @return 控件底部的Y点
         */
        fun getScreenViewBottomHeight(view: View): Int {
            return view.bottom
        }

        /**
         * 获取屏幕中控件左侧的位置--即控件左侧的X点
         *
         * @return 控件左侧的X点
         */
        fun getScreenViewLeftHeight(view: View): Int {
            return view.left
        }

        /**
         * 获取屏幕中控件右侧的位置--即控件右侧的X点
         *
         * @return 控件右侧的X点
         */
        fun getScreenViewRightHeight(view: View): Int {
            return view.right
        }
    }
}
