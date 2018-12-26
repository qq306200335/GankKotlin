package com.xiaobai.libview.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.xiaobai.libutils.common.DensityUtils

/**
 * 确认删除对话框
 *
 * @author baiyunfei on 2016/3/31.
 */
class SureDialog(private val context: Context) {

    private var iSureDialogBack: ISureDialogBack? = null

    /**
     * 提示标题
     */
    private var title = ""
    /**
     * 提示内容
     */
    private var content = "确定删除？"

    fun setTitle(title: String) {
        this.title = title
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun show() {

        val builder = AlertDialog.Builder(context)

        if (title.isNotEmpty()) {
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
        builder.setMessage(content)

        builder.setPositiveButton("确定") { dialog, which -> iSureDialogBack!!.sure() }
        builder.setNegativeButton("取消") { dialog, which -> }

        val alertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#718DD5"))
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#777777"))
    }

    /**
     * 设置回调接口
     *
     * @param iSureDialogBack 回调接口
     */
    fun setISureDialogBack(iSureDialogBack: ISureDialogBack) {
        this.iSureDialogBack = iSureDialogBack
    }

    interface ISureDialogBack {

        /**
         * 确定
         */
        fun sure()
    }
}