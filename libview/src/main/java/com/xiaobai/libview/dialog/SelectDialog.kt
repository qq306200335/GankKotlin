package com.xiaobai.libview.dialog

import android.app.AlertDialog
import android.content.Context

/**
 * 选择对话框
 *
 * @author baiyunfei on 2016/3/31
 * email 306200335@qq.com
 */
class SelectDialog(private val context: Context) {

    private var iSelectDialogBack: ISelectDialogBack? = null
    private var strings: Array<String>? = null
    private var position: Int = 0
    private var isCancel = true

    fun setStrings(strings: Array<String>, position: Int) {
        this.strings = strings
        this.position = position
    }

    fun show() {

        val builder = AlertDialog.Builder(context)

        if (strings == null) {
            return
        }

        builder.setSingleChoiceItems(strings, position) { dialogInterface, i ->
            dialogInterface.dismiss()
            if (iSelectDialogBack != null) {
                iSelectDialogBack!!.getPosition(i)
            }
        }

        val selectDialog = builder.create()
        selectDialog.setCanceledOnTouchOutside(isCancel)
        selectDialog.show()
    }

    fun setCancel(isCancel: Boolean) {
        this.isCancel = isCancel
    }

    /**
     * 设置回调接口
     *
     * @param iSelectDialogBack 回调接口
     */
    fun setISelectDialogBack(iSelectDialogBack: ISelectDialogBack) {
        this.iSelectDialogBack = iSelectDialogBack
    }

    interface ISelectDialogBack {

        /**
         * 确定
         *
         * @param position 选择的位置
         */
        fun getPosition(position: Int)
    }
}
