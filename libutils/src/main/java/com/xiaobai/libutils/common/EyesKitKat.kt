package com.xiaobai.libutils.common

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.xiaobai.libutils.R

import java.lang.reflect.Field

/**
 * 来自网络
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
object EyesKitKat {
    private val TAG_FAKE_STATUS_BAR_VIEW = "statusBarView"
    private val TAG_MARGIN_ADDED = "marginAdded"

    internal fun setStatusBarColor(activity: Activity, statusColor: Int) {
        val window = activity.window
        //设置Window为全透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val mContentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        //获取父布局
        val mContentChild = mContentView.getChildAt(0)
        //获取状态栏高度
        val statusBarHeight = getStatusBarHeight(activity)

        //如果已经存在假状态栏则移除，防止重复添加
        removeFakeStatusBarViewIfExist(activity)
        //添加一个View来作为状态栏的填充
        addFakeStatusBarView(activity, statusColor, statusBarHeight)
        //设置子控件到状态栏的间距
        addMarginTopToContentChild(mContentChild, statusBarHeight)
        //不预留系统栏位置
        if (mContentChild != null) {
            mContentChild.fitsSystemWindows = false
        }
        //如果在Activity中使用了ActionBar则需要再将布局与状态栏的高度跳高一个ActionBar的高度，否则内容会被ActionBar遮挡
        val action_bar_id = activity.resources.getIdentifier("action_bar", "id", activity.packageName)
        val view = activity.findViewById<View>(action_bar_id)
        if (view != null) {
            val typedValue = TypedValue()
            if (activity.theme.resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                val actionBarHeight =
                    TypedValue.complexToDimensionPixelSize(typedValue.data, activity.resources.displayMetrics)
                Eyes.setContentTopPadding(activity, actionBarHeight)
            }
        }
    }

    internal fun translucentStatusBar(activity: Activity) {
        val window = activity.window
        //设置Window为透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val mContentView = activity.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val mContentChild = mContentView.getChildAt(0)

        //移除已经存在假状态栏则,并且取消它的Margin间距
        removeFakeStatusBarViewIfExist(activity)
        removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity))
        if (mContentChild != null) {
            //fitsSystemWindow 为 false, 不预留系统栏位置.
            mContentChild.fitsSystemWindows = false
        }
    }

    internal fun setStatusBarColorForCollapsingToolbar(
        activity: Activity, appBarLayout: AppBarLayout, collapsingToolbarLayout: CollapsingToolbarLayout,
        toolbar: Toolbar, statusColor: Int
    ) {
        val window = activity.window
        //设置Window为全透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val mContentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)

        //AppBarLayout,CollapsingToolbarLayout,ToolBar,ImageView的fitsSystemWindow统一改为false, 不预留系统栏位置.
        val mContentChild = mContentView.getChildAt(0)
        mContentChild.fitsSystemWindows = false
        (appBarLayout.parent as View).fitsSystemWindows = false
        appBarLayout.fitsSystemWindows = false
        collapsingToolbarLayout.fitsSystemWindows = false
        collapsingToolbarLayout.getChildAt(0).fitsSystemWindows = false

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
        //移除已经存在假状态栏则,并且取消它的Margin间距
        val statusBarHeight = getStatusBarHeight(activity)
        removeFakeStatusBarViewIfExist(activity)
        removeMarginTopOfContentChild(mContentChild, statusBarHeight)
        //添加一个View来作为状态栏的填充
        val statusView = addFakeStatusBarView(activity, statusColor, statusBarHeight)

        val behavior = (appBarLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior
        if (behavior != null && behavior is AppBarLayout.Behavior) {
            val verticalOffset = behavior.topAndBottomOffset
            if (Math.abs(verticalOffset) > appBarLayout.height - collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                statusView.alpha = 1f
            } else {
                statusView.alpha = 0f
            }
        } else {
            statusView.alpha = 0f
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) > appBarLayout.height - collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                //toolbar被折叠时显示状态栏
                if (statusView.alpha == 0f) {
                    statusView.animate().cancel()
                    statusView.animate().alpha(1f).setDuration(collapsingToolbarLayout.scrimAnimationDuration).start()
                }
            } else {
                //toolbar展开时显示状态栏
                if (statusView.alpha == 1f) {
                    statusView.animate().cancel()
                    statusView.animate().alpha(0f).setDuration(collapsingToolbarLayout.scrimAnimationDuration).start()
                }
            }
        })
    }

    internal fun setStatusBarWhiteForCollapsingToolbar(
        activity: Activity,
        appBarLayout: AppBarLayout,
        collapsingToolbarLayout: CollapsingToolbarLayout,
        toolbar: Toolbar,
        statusBarColor: Int
    ) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val mContentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val mContentChild = mContentView.getChildAt(0)
        mContentChild.fitsSystemWindows = false
        (appBarLayout.parent as View).fitsSystemWindows = false
        appBarLayout.fitsSystemWindows = false
        collapsingToolbarLayout.fitsSystemWindows = false
        collapsingToolbarLayout.getChildAt(0).fitsSystemWindows = false
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

        val statusBarHeight = getStatusBarHeight(activity)
        var color = Color.BLACK
        try {
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            color = statusBarColor
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        try {
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            color = statusBarColor
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        val statusView = addFakeStatusBarView(activity, color, statusBarHeight)
        val behavior = (appBarLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior
        if (behavior != null && behavior is AppBarLayout.Behavior) {
            val verticalOffset = behavior.topAndBottomOffset
            if (Math.abs(verticalOffset) > appBarLayout.height - collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                statusView.alpha = 1f
            } else {
                statusView.alpha = 0f
            }
        } else {
            statusView.alpha = 0f
        }

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            private val EXPANDED = 0
            private val COLLAPSED = 1
            private var appBarLayoutState: Int = 0

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange - Eyes.getPxFromDp(activity, 56f)) {
                    if (appBarLayoutState != COLLAPSED) {
                        appBarLayoutState = COLLAPSED

                        if (Eyes.MIUISetStatusBarLightMode(activity, true) || Eyes.FlymeSetStatusBarLightMode(
                                activity,
                                true
                            )
                        ) {
                        }
                        if (statusView.alpha == 0f) {
                            statusView.animate().cancel()
                            statusView.animate().alpha(1f).setDuration(collapsingToolbarLayout.scrimAnimationDuration)
                                .start()
                        }
                    }
                } else {
                    if (appBarLayoutState != EXPANDED) {
                        appBarLayoutState = EXPANDED

                        if (Eyes.MIUISetStatusBarLightMode(activity, false) || Eyes.FlymeSetStatusBarLightMode(
                                activity,
                                false
                            )
                        ) {
                        }
                        if (statusView.alpha == 1f) {
                            statusView.animate().cancel()
                            statusView.animate().alpha(0f).setDuration(collapsingToolbarLayout.scrimAnimationDuration)
                                .start()
                        }
                        translucentStatusBar(activity)
                    }
                }
            }
        })
    }

    private fun removeFakeStatusBarViewIfExist(activity: Activity) {
        val window = activity.window
        val mDecorView = window.decorView as ViewGroup

        val fakeView = mDecorView.findViewWithTag<View>(TAG_FAKE_STATUS_BAR_VIEW)
        if (fakeView != null) {
            mDecorView.removeView(fakeView)
        }
    }

    private fun addFakeStatusBarView(activity: Activity, statusBarColor: Int, statusBarHeight: Int): View {
        val window = activity.window
        val mDecorView = window.decorView as ViewGroup

        val mStatusBarView = View(activity)
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
        layoutParams.gravity = Gravity.TOP
        mStatusBarView.layoutParams = layoutParams
        mStatusBarView.setBackgroundColor(statusBarColor)
        mStatusBarView.tag = TAG_FAKE_STATUS_BAR_VIEW

        mDecorView.addView(mStatusBarView)
        return mStatusBarView
    }

    private fun addMarginTopToContentChild(mContentChild: View?, statusBarHeight: Int) {
        if (mContentChild == null) {
            return
        }
        if (TAG_MARGIN_ADDED != mContentChild.tag) {
            val lp = mContentChild.layoutParams as FrameLayout.LayoutParams
            lp.topMargin += statusBarHeight
            mContentChild.layoutParams = lp
            mContentChild.tag = TAG_MARGIN_ADDED
        }
    }

    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            result = context.resources.getDimensionPixelOffset(resId)
        }
        return result
    }

    private fun removeMarginTopOfContentChild(mContentChild: View?, statusBarHeight: Int) {
        if (mContentChild == null) {
            return
        }
        if (TAG_MARGIN_ADDED == mContentChild.tag) {
            val lp = mContentChild.layoutParams as FrameLayout.LayoutParams
            lp.topMargin -= statusBarHeight
            mContentChild.layoutParams = lp
            mContentChild.tag = null
        }
    }
}
