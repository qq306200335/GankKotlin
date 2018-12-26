package com.xiaobai.libutils.common

import android.content.Context
import android.widget.Toast

/**
 * 自定义Toast
 *
 * @author 来自网络 on 2015/7/14
 */
object MyToast {

    fun showToast(con: Context, text: String) {
        Toast.makeText(con, text, Toast.LENGTH_SHORT).show()
    }

    fun showToastLong(con: Context, text: String) {
        Toast.makeText(con, text, Toast.LENGTH_LONG).show()
    }

    fun showToast(con: Context, id: Int) {
        Toast.makeText(con, id, Toast.LENGTH_SHORT).show()
    }

    fun showToastLong(con: Context, id: Int) {
        Toast.makeText(con, id, Toast.LENGTH_LONG).show()
    }

}
