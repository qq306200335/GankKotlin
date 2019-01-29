package com.xiaobai.gankkotlin.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xiaobai.gankkotlin.base.interfaces.IBaseView
import com.xiaobai.gankkotlin.interfaces.IToolBarBack
import com.xiaobai.libutils.common.MyLog
import com.xiaobai.libview.dialog.ProgressDialog

/**
 * 通用Fragment
 * @author baiyunfei on 2018/11/28
 * email 306200335@qq.com
 */
abstract class BaseFragment : Fragment(), IBaseView, View.OnClickListener {

    protected var mContext: Context? = null
    protected var mView: View? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false)
            loadPresenter()
            initView(mView!!)
            initListener()
            initData()
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun loadPresenter()

    protected abstract fun initView(view: View): View

    protected abstract fun initListener()

    protected abstract fun initData()

    protected abstract fun otherViewClick(view: View)

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    override fun onClick(view: View) {
        otherViewClick(view)
    }

    override fun initToolBar(title: String, isBack: Boolean) {
    }

    override fun setToolBarBack(iToolBarBack: IToolBarBack) {
    }

    override fun showToolBar(isShow: Boolean) {
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
            progressDialog = ProgressDialog.createDialog(mContext!!)
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
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * @param str 日志的处理
     */
    override fun log(str: String) {
        MyLog.e(javaClass.simpleName, str)
    }

}