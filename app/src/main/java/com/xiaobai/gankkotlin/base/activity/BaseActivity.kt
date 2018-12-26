package com.xiaobai.gankkotlin.base.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.xiaobai.gankkotlin.R
import com.xiaobai.gankkotlin.app.AppManager
import com.xiaobai.gankkotlin.base.interfaces.IBaseView
import com.xiaobai.gankkotlin.interfaces.IToolBarBack
import com.xiaobai.libutils.common.MyLog
import com.xiaobai.libview.dialog.ProgressDialog

/**
 * 通用Activity
 *
 * @author baiyunfei on 2017/11/22.
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView, View.OnClickListener {

    private var toolbarTitleTv: TextView? = null
    private var toolbarCl: ConstraintLayout? = null
    private var progressDialog: ProgressDialog? = null

    private var toolBarBack: IToolBarBack? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())
        AppManager.appManager.addActivity(this)
        loadPresenter()
        initView()
        initListener()
        initData()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun loadPresenter()

    protected abstract fun initView()

    protected abstract fun initListener()

    protected abstract fun initData()

    protected abstract fun otherViewClick(view: View)

    /**
     * @return 显示的内容
     */
    open fun getView(): View {
        return View.inflate(this, getLayoutId(), null)
    }

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    override fun onClick(view: View) {
        otherViewClick(view)
    }

    override fun initToolBar(title: String, isBack: Boolean) {

        toolbarCl = findViewById(R.id.toolbar_head)
        toolbarTitleTv = findViewById(R.id.title_tv_toolbar)
        val backIv: ImageView = findViewById(R.id.back_iv_toolbar)

        toolbarTitleTv!!.text = title

        if (isBack) {
            backIv.visibility = View.VISIBLE
            backIv.setOnClickListener { view: View? ->
                if (toolBarBack == null) {
                    AppManager.appManager.finishActivity()
                } else {
                    toolBarBack!!.onClick(view!!)
                }
            }
        }
    }

    fun setToolBarTitle(title: String) {
        if (toolbarTitleTv != null) {
            toolbarTitleTv!!.text = title
        }
    }

    override fun setToolBarBack(iToolBarBack: IToolBarBack) {
        toolBarBack = iToolBarBack
    }

    override fun showToolBar(isShow: Boolean) {
        if (isShow) {
            toolbarCl!!.visibility = View.VISIBLE
        } else {
            toolbarCl!!.visibility = View.GONE
        }
    }

    /**
     * 设置进度对话框是否可以点击空白处消失
     */
    fun setLoadingCancelable(isCancelable: Boolean) {
        progressDialog!!.setCancelable(isCancelable)
    }

    /**
     * 显示进度对话框
     */
    override fun showLoading() {

        if (progressDialog == null) {
            progressDialog = ProgressDialog.createDialog(this)
        }

        if (!progressDialog!!.isShowing) {
            progressDialog!!.show()
        }
    }

    /**
     * 隐藏进度对话框
     */
    override fun hideLoading() {

        if (progressDialog == null) {
            return
        }

        if (progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    override fun showError(error: String) {
       toast(error)
    }

    override fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * @param str 日志的处理
     */
    override fun log(str: String) {
        MyLog.e(javaClass.simpleName, str)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {

        if (KeyEvent.ACTION_DOWN == event.action && KeyEvent.KEYCODE_BACK == event.keyCode) {

            AppManager.appManager.finishActivity()

            return true
        }
        return super.dispatchKeyEvent(event)
    }
}
