package com.xiaobai.libutils.common

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * @author baiyunfei on 2019/1/25
 * email 306200335@qq.com
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
internal object EyesLollipop {

    fun setStatusBarColor(activity: Activity, statusColor: Int) {
        val window = activity.window
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //设置状态栏颜色
        window.statusBarColor = statusColor
        //设置系统状态栏处于可见状态
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        //让view不根据系统窗口来调整自己的布局
        val mContentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val mChildView = mContentView.getChildAt(0)
        if (mChildView != null) {
            mChildView.fitsSystemWindows = false
            ViewCompat.requestApplyInsets(mChildView)
        }
    }

    fun translucentStatusBar(activity: Activity, hideStatusBarBackground: Boolean) {
        val window = activity.window
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (hideStatusBarBackground) {
            //如果为全透明模式，取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //设置状态栏为透明
            window.statusBarColor = Color.TRANSPARENT
            //设置window的状态栏不可见
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            //如果为半透明模式，添加设置Window半透明的Flag
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //设置系统状态栏处于可见状态
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
        //view不根据系统窗口来调整自己的布局
        val mContentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val mChildView = mContentView.getChildAt(0)
        if (mChildView != null) {
            mChildView.fitsSystemWindows = false
            ViewCompat.requestApplyInsets(mChildView)
        }
    }

    fun setStatusBarColorForCollapsingToolbar(
        activity: Activity, appBarLayout: AppBarLayout, collapsingToolbarLayout: CollapsingToolbarLayout,
        toolbar: Toolbar, statusColor: Int
    ) {
        val window = activity.window
        //取消设置Window半透明的Flag
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        ////添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //设置状态栏为透明
        window.statusBarColor = Color.TRANSPARENT
        //设置系统状态栏处于可见状态
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        //通过OnApplyWindowInsetsListener()使Layout在绘制过程中将View向下偏移了,使collapsingToolbarLayout可以占据状态栏
        ViewCompat.setOnApplyWindowInsetsListener(collapsingToolbarLayout) { v, insets -> insets }

        val mContentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val mChildView = mContentView.getChildAt(0)
        //view不根据系统窗口来调整自己的布局
        if (mChildView != null) {
            mChildView.fitsSystemWindows = false
            ViewCompat.requestApplyInsets(mChildView)
        }

        (appBarLayout.parent as View).fitsSystemWindows = false
        appBarLayout.fitsSystemWindows = false
        collapsingToolbarLayout.fitsSystemWindows = false
        collapsingToolbarLayout.getChildAt(0).fitsSystemWindows = false
        //设置状态栏的颜色
        collapsingToolbarLayout.setStatusBarScrimColor(statusColor)
        toolbar.fitsSystemWindows = false
        //为Toolbar添加一个状态栏的高度, 同时为Toolbar添加paddingTop,使Toolbar覆盖状态栏，ToolBar的title可以正常显示.
        if (toolbar.tag == null) {
            val lp = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
            val statusBarHeight = getStatusBarHeight(activity)
            lp.height += statusBarHeight
            toolbar.layoutParams = lp
            toolbar.setPadding(
                toolbar.paddingLeft,
                toolbar.paddingTop + statusBarHeight,
                toolbar.paddingRight,
                toolbar.paddingBottom
            )
            toolbar.tag = true
        }

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            private val EXPANDED = 0
            private val COLLAPSED = 1
            private var appBarLayoutState: Int = 0

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                //toolbar被折叠时显示状态栏
                if (Math.abs(verticalOffset) > collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                    if (appBarLayoutState != COLLAPSED) {
                        appBarLayoutState = COLLAPSED//修改状态标记为折叠
                        setStatusBarColor(activity, statusColor)
                    }
                } else {
                    //toolbar显示时同时显示状态栏
                    if (appBarLayoutState != EXPANDED) {
                        appBarLayoutState = EXPANDED//修改状态标记为展开
                        translucentStatusBar(activity, true)
                    }
                }
            }
        })
    }

    fun setStatusBarWhiteForCollapsingToolbar(
        activity: Activity,
        appBarLayout: AppBarLayout,
        collapsingToolbarLayout: CollapsingToolbarLayout,
        toolbar: Toolbar,
        statusBarColor: Int
    ) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

        ViewCompat.setOnApplyWindowInsetsListener(collapsingToolbarLayout) { v, insets -> insets }

        val mContentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val mChildView = mContentView.getChildAt(0)
        if (mChildView != null) {
            mChildView.fitsSystemWindows = false
            ViewCompat.requestApplyInsets(mChildView)
        }

        (appBarLayout.parent as View).fitsSystemWindows = false
        appBarLayout.fitsSystemWindows = false
        toolbar.fitsSystemWindows = false
        if (toolbar.tag == null) {
            val lp = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
            val statusBarHeight = getStatusBarHeight(activity)
            lp.height += statusBarHeight
            toolbar.layoutParams = lp
            toolbar.setPadding(
                toolbar.paddingLeft,
                toolbar.paddingTop + statusBarHeight,
                toolbar.paddingRight,
                toolbar.paddingBottom
            )
            toolbar.tag = true
        }

        collapsingToolbarLayout.fitsSystemWindows = false
        collapsingToolbarLayout.getChildAt(0).fitsSystemWindows = false
        collapsingToolbarLayout.setStatusBarScrimColor(statusBarColor)
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            private val EXPANDED = 0
            private val COLLAPSED = 1
            private var appBarLayoutState: Int = 0

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                //toolbar被折叠时显示状态栏
                if (Math.abs(verticalOffset) > collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                    if (appBarLayoutState != COLLAPSED) {
                        appBarLayoutState = COLLAPSED//修改状态标记为折叠

                        //先判断是否为小米设备，设置状态栏不成功判断是否为6.0以上设备，不是6.0以上设备再判断是否为魅族设备，不是魅族设备就只设置状态栏颜色
                        if (Eyes.MIUISetStatusBarLightMode(activity, true)) {
                            return
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            activity.window.setBackgroundDrawableResource(android.R.color.transparent)
                            activity.window.decorView.systemUiVisibility =
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            activity.window.statusBarColor = statusBarColor
                        } else if (!Eyes.FlymeSetStatusBarLightMode(activity, true)) {
                            setStatusBarColor(activity, statusBarColor)
                        }
                    }
                } else {
                    //toolbar显示时同时显示状态栏
                    if (appBarLayoutState != EXPANDED) {
                        appBarLayoutState = EXPANDED//修改状态标记为展开

                        if (Eyes.MIUISetStatusBarLightMode(activity, false)) {
                            translucentStatusBar(activity, true)
                            return
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        } else if (Eyes.FlymeSetStatusBarLightMode(activity, true)) {
                        }
                        translucentStatusBar(activity, true)
                    }
                }
            }
        })
    }

    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            result = context.resources.getDimensionPixelOffset(resId)
        }
        return result
    }
}
