package com.xiaobai.libview.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.xiaobai.libutils.common.DensityUtils

/**
 * 自定义对话框
 *
 * @author baiyunfei on 2016/12/2.
 */

class MyDialog(private val context: Context) {

    private var iMyDialogBack: IMyDialogBack? = null

    /**
     * 提示标题，内容
     */
    private var title = ""
    private var content = ""

    fun setTitle(title: String) {
        this.title = title
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun show() {
        val builder = AlertDialog.Builder(context)

        if ("" != title) {
            builder.setTitle(title)
            val tv = TextView(context)
            tv.text = title
            tv.textSize = 20f
            tv.setPadding(
                DensityUtils.dip2px(context, 23f),
                DensityUtils.dip2px(context, 24f),
                DensityUtils.dip2px(context, 23f),
                DensityUtils.dip2px(context, 4f)
            )
            tv.setTextColor(Color.parseColor("#333333"))
            builder.setCustomTitle(tv)
        }

        if ("" != content) {
            builder.setMessage(content)
        }

        builder.setPositiveButton("确定") { dialog, which ->
            if (iMyDialogBack != null) {
                iMyDialogBack!!.sure()
            }
        }
        builder.setNegativeButton("取消") { dialog, which ->
            if (iMyDialogBack != null) {
                iMyDialogBack!!.cancel()
            }
        }

        val alertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#718DD5"))
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#777777"))
    }

    /**
     * 设置回调接口
     *
     * @param iMyDialogBack 回调接口
     */
    fun setIMyDialogBack(iMyDialogBack: IMyDialogBack) {
        this.iMyDialogBack = iMyDialogBack
    }

    interface IMyDialogBack {

        /**
         * 确定
         */
        fun sure()

        /**
         * 取消
         */
        fun cancel()
    }
}
