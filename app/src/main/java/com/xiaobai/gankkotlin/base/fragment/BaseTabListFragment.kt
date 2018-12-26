package com.xiaobai.gankkotlin.base.fragment

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import com.kzcpm.librefresh.view.DWRefreshLayout
import com.kzcpm.librefresh.view.MaterialHeadView
import com.xiaobai.gankkotlin.R
import com.xiaobai.gankkotlin.base.interfaces.IBaseListView

/**
 * 通用TabListFragment(懒加载列表专用)
 * Created by 30620 on 2018/1/16.
 */

abstract class BaseTabListFragment : BaseTabFragment(), IBaseListView {

    private lateinit var refreshLayout: DWRefreshLayout
    private lateinit var noticeTv: TextView

    private var handler = Handler(Looper.getMainLooper())

    override fun initView(mView: View) {
        refreshLayout = mView.findViewById(R.id.refresh_list_a)
        noticeTv = mView.findViewById(R.id.notice_tv_list_a)
    }

    //初始化刷新
    fun initRefresh() {

        //设置Material 刷新头 ( 注意要在布局文件设置这个属性 app:refresh_style="style_material")
        val materialHeadView = MaterialHeadView(activity)
        materialHeadView.setColorSchemeColors(Color.BLACK)
        refreshLayout.setHeadView(materialHeadView)
        refreshLayout.setOnRefreshListener(object : DWRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                handler.postDelayed({ dataRefresh() }, 500)
            }

            override fun onLoadMore() {
                handler.postDelayed({ dataLoadMore() }, 500)
            }
        })

        setRefresh()
    }

    //刷新后操作
    abstract fun dataRefresh()

    //加载后操作
    abstract fun dataLoadMore()

    /**
     * 刷新列表
     */
    override fun refreshAdapter() {
    }

    override fun setRefresh() {
        refreshLayout.isRefresh = true
    }

    /**
     * 设置完成刷新
     */
    override fun setRefreshComplete() {
        refreshLayout.isRefresh = false
    }

    /**
     * 设置是否加载更多
     */
    override fun setLoadMoreEnable(isLoadMore: Boolean) {
        refreshLayout.lockLoadMore(!isLoadMore)
    }

    /**
     * 显示刷新失败信息
     */
    override fun noticeShow(info: String) {
        noticeTv.text = info
        noticeTv.visibility = View.VISIBLE
    }

    /**
     * 隐藏刷新失败信息
     */
    override fun noticeDismiss() {
        noticeTv.visibility = View.GONE
    }
}
