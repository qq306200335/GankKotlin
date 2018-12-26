package com.xiaobai.libview.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import com.xiaobai.libview.R

/**
 * 网络请求进度
 *
 * @author baiyunfei on 2015/9/14
 * email 306200335@qq.com
 */
class ProgressDialog : Dialog {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, theme: Int) : super(context, theme) {}

    override fun onWindowFocusChanged(hasFocus: Boolean) {

        if (progressDialog == null) {
            return
        }
    }

    companion object {

        private var progressDialog: ProgressDialog? = null

        fun createDialog(context: Context): ProgressDialog {
            progressDialog = ProgressDialog(context, R.style.ProgressDialog)
            progressDialog!!.setContentView(R.layout.dialog_progress)
            progressDialog!!.window!!.attributes.gravity = Gravity.CENTER
            progressDialog!!.setCanceledOnTouchOutside(false)

            return progressDialog as ProgressDialog
        }
    }
}
