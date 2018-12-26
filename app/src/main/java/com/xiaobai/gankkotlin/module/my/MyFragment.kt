package com.xiaobai.gankkotlin.module.my


import android.os.Bundle
import android.view.View
import com.xiaobai.gankkotlin.R
import com.xiaobai.gankkotlin.base.fragment.BaseFragment

/**
 * 我的界面
 *
 * @author baiyunfei on 2018/11/28
 */
class MyFragment : BaseFragment() {

    companion object {
        fun newInstance(param: String): MyFragment {
            val fragment = MyFragment()
            val args = Bundle()
            args.putString("我的", param)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_my
    }

    override fun loadPresenter() {
    }

    override fun initView(mView: View) {
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun otherViewClick(view: View) {
    }

}
