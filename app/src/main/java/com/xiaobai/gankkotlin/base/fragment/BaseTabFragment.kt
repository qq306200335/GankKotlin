package com.xiaobai.gankkotlin.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 通用TabFragment(懒加载专用)
 * @author baiyunfei on 2018/11/28
 * email 306200335@qq.com
 */
abstract class BaseTabFragment : BaseFragment() {

    //是否正在显示
    private var isShow: Boolean = false
    private var isPrepared: Boolean = false
    private var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false)
            loadPresenter()
            initView(mView!!)
            initListener()
            //initData();
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        isPrepared = true
        lazyLoad()
    }

    /**
     * 懒加载设置
     *
     * @param isVisibleToUser 是否显示
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isShow = true
            lazyLoad()
        } else {
            isShow = false
            onInvisible()
        }
    }

    /**
     * 懒加载
     */
    private fun lazyLoad() {

        if (!isPrepared || !isShow) {
            return
        }

        if (isFirst) {
            isFirst = false
            initData()
        }
    }

    // 不可见
    private fun onInvisible() {
    }
}