package com.xiaobai.libutils.common

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * 来自网络
 */
object Eyes {

    fun setStatusBarColor(activity: Activity, statusColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.setStatusBarColor(activity, statusColor)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.setStatusBarColor(activity, statusColor)
        }
    }

    @JvmOverloads
    fun translucentStatusBar(activity: Activity, hideStatusBarBackground: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.translucentStatusBar(activity, hideStatusBarBackground)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.translucentStatusBar(activity)
        }
    }

    fun setStatusBarColorForCollapsingToolbar(
        activity: Activity, appBarLayout: AppBarLayout, collapsingToolbarLayout: CollapsingToolbarLayout,
        toolbar: Toolbar, @ColorInt statusColor: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.setStatusBarColorForCollapsingToolbar(
                activity,
                appBarLayout,
                collapsingToolbarLayout,
                toolbar,
                statusColor
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.setStatusBarColorForCollapsingToolbar(
                activity,
                appBarLayout,
                collapsingToolbarLayout,
                toolbar,
                statusColor
            )
        }
    }

    fun setStatusBarLightMode(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //判断是否为小米或魅族手机，如果是则将状态栏文字改为黑色
            if (MIUISetStatusBarLightMode(activity, true) || FlymeSetStatusBarLightMode(activity, true)) {
                //设置状态栏为指定颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    activity.window.statusBarColor = color
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                    //调用修改状态栏颜色的方法
                    setStatusBarColor(activity, color)
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
                activity.window.setBackgroundDrawableResource(android.R.color.transparent)
                activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                activity.window.statusBarColor = color

                //fitsSystemWindow 为 false, 不预留系统栏位置.
                val mContentView = activity.window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
                val mChildView = mContentView.getChildAt(0)
                if (mChildView != null) {
                    mChildView.fitsSystemWindows = true
                    ViewCompat.requestApplyInsets(mChildView)
                }
            }
        }
    }


    fun setStatusBarLightForCollapsingToolbar(
        activity: Activity, appBarLayout: AppBarLayout,
        collapsingToolbarLayout: CollapsingToolbarLayout, toolbar: Toolbar, statusBarColor: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.setStatusBarWhiteForCollapsingToolbar(
                activity,
                appBarLayout,
                collapsingToolbarLayout,
                toolbar,
                statusBarColor
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.setStatusBarWhiteForCollapsingToolbar(
                activity,
                appBarLayout,
                collapsingToolbarLayout,
                toolbar,
                statusBarColor
            )
        }
    }


    /**
     * MIUI的沉浸支持透明白色字体和透明黑色字体
     * https://dev.mi.com/console/doc/detail?pId=1159
     */
    internal fun MIUISetStatusBarLightMode(activity: Activity, darkmode: Boolean): Boolean {
        try {
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")

            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            val clazz = activity.window.javaClass
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagField =
                clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.invoke(activity.window, if (darkmode) darkModeFlag else 0, darkModeFlag)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     */
    internal fun FlymeSetStatusBarLightMode(activity: Activity, darkmode: Boolean): Boolean {
        try {
            val lp = activity.window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            if (darkmode) {
                value = value or bit
            } else {
                value = value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            activity.window.attributes = lp
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    internal fun setContentTopPadding(activity: Activity, padding: Int) {
        val mContentView = activity.window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        mContentView.setPadding(0, padding, 0, 0)
    }

    internal fun getPxFromDp(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }
}
