package com.xiaobai.gankkotlin.base.interfaces

/**
 * 列表界面通用view
 * Created by 30620 on 2017/12/29.
 */

interface IBaseListView : IBaseView {

    fun refreshAdapter()

    fun setRefresh()

    fun setRefreshComplete()

    fun setLoadMoreEnable(isLoadMore: Boolean)

    fun noticeShow(info: String)

    fun noticeDismiss()
}
