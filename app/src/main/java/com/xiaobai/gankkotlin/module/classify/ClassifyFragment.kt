package com.xiaobai.gankkotlin.module.classify


import android.os.Bundle
import android.view.View
import com.xiaobai.gankkotlin.R
import com.xiaobai.gankkotlin.base.fragment.BaseFragment

/**
 * 分类界面
 *
 * @author baiyunfei on 2018/11/28
 */
class ClassifyFragment : BaseFragment() {

    companion object {
        fun newInstance(param: String): ClassifyFragment {
            val fragment = ClassifyFragment()
            val args = Bundle()
            args.putString("分类", param)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_classify
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
