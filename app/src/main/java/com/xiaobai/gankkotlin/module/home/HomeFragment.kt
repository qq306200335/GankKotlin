package com.xiaobai.gankkotlin.module.home


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kzcpm.librefresh.view.DWRefreshLayout
import com.kzcpm.librefresh.view.MaterialHeadView
import com.xiaobai.gankkotlin.R
import com.xiaobai.gankkotlin.adapter.DataInfoAdapter
import com.xiaobai.gankkotlin.base.fragment.BaseFragment

/**
 * 首页界面
 *
 * @author baiyunfei on 2018/11/28
 */
class HomeFragment : BaseFragment(), HomeContract.HomeView {

    private lateinit var refreshLayout: DWRefreshLayout
    private lateinit var dataRv: RecyclerView
    private lateinit var noticeTv: TextView

    private lateinit var dataInfoAdapter: DataInfoAdapter
    private val handler = Handler(Looper.getMainLooper())

    private val mPresenter: HomePresenter = HomePresenter()

    companion object {
        fun newInstance(param: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("首页", param)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun loadPresenter() {
        mPresenter.attachView(this)
    }

    override fun initView(view: View): View {
        refreshLayout = view.findViewById(R.id.refresh_list_a)
        dataRv = view.findViewById(R.id.data_rv_list_a)
        noticeTv = view.findViewById(R.id.notice_tv_list_a)

        val toolbarTitleTv: TextView = view.findViewById(R.id.title_tv_toolbar)
        toolbarTitleTv.text = "Gank"

        return view
    }

    override fun initListener() {
    }

    override fun initData() {

        dataInfoAdapter = DataInfoAdapter(context!!, mPresenter.dataInfoList)
        dataRv.layoutManager = LinearLayoutManager(activity)
        // 设置item动画
        dataRv.itemAnimator = DefaultItemAnimator()
        dataRv.adapter = dataInfoAdapter

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

    override fun otherViewClick(view: View) {
    }

    fun dataRefresh() {
        mPresenter.number = 1
        mPresenter.getGankListRequest()
    }

    fun dataLoadMore() {
        mPresenter.number = mPresenter.number + 1
        mPresenter.getGankListRequest()
    }

    /**
     * 刷新列表
     */
    override fun refreshAdapter() {
        dataInfoAdapter.notifyDataSetChanged()
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
