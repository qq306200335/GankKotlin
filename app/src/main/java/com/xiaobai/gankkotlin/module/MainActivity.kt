package com.xiaobai.gankkotlin.module

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.xiaobai.gankkotlin.R
import com.xiaobai.gankkotlin.base.activity.ApiActivity
import com.xiaobai.gankkotlin.model.MainModel
import com.xiaobai.gankkotlin.module.classify.ClassifyFragment
import com.xiaobai.gankkotlin.module.home.HomeFragment
import com.xiaobai.gankkotlin.module.my.MyFragment

/**
 * 项目主界面
 *
 * @author baiyunfei on 2018/11/22.
 */
class MainActivity : ApiActivity(), MainView {

    private lateinit var bottomNavigationView: BottomNavigationView

    //当前显示的fragment
    private var nowFragment: Fragment? = null
    private var homeFragment: HomeFragment? = null
    private var classifyFragment: ClassifyFragment? = null
    private var myFragment: MyFragment? = null

    private val mPresenter: MainPresenter = MainPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun loadPresenter() {
        mPresenter.attachView(this)
    }

    override fun initView() {
        bottomNavigationView = findViewById(R.id.navigation_main_a)
    }

    override fun initListener() {
    }

    @SuppressLint("ResourceType")
    override fun initData() {

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView.itemTextColor = resources.getColorStateList(R.drawable.select_main_color)
        bottomNavigationView.itemIconTintList = null

        setDefaultFragment()
    }

    override fun otherViewClick(view: View) {
    }

    private fun setDefaultFragment() {

        //获取FragmentManager
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance("微现场")
        }

        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!homeFragment!!.isAdded) {
            // 提交事务
            transaction.add(R.id.main_fl_main_a, homeFragment!!).commit()
            // 记录当前Fragment
            nowFragment = homeFragment
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_home -> {
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.newInstance("微现场")
                    }
                    addOrShowFragment(homeFragment!!)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_classify -> {
                    if (classifyFragment == null) {
                        classifyFragment = ClassifyFragment.newInstance("分类")
                    }
                    addOrShowFragment(classifyFragment!!)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_my -> {
                    if (myFragment == null) {
                        myFragment = MyFragment.newInstance("我的")
                    }
                    addOrShowFragment(myFragment!!)

                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                }
            }
            false
        }

    /**
     * 添加或者显示 fragment
     *
     * @param fragment 需要显示或添加的fragment
     */
    private fun addOrShowFragment(fragment: Fragment) {

        if (nowFragment === fragment) {
            return
        }

        //获取FragmentManager
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!fragment.isAdded) {
            transaction.hide(nowFragment!!).add(R.id.main_fl_main_a, fragment).commit()
        } else {
            transaction.hide(nowFragment!!).show(fragment).commit()
        }

        nowFragment!!.userVisibleHint = false
        nowFragment = fragment
        nowFragment!!.userVisibleHint = true
    }

    fun getNowFragment(): Fragment {
        return nowFragment!!
    }

    fun getTabPosition(): Int {
        return bottomNavigationView.verticalScrollbarPosition
    }

    /**
     * 设置底部导航显示的位置
     *
     * @param position 位置
     */
    fun setTabSelect(position: Int) {

        when (position) {
            0 -> bottomNavigationView.selectedItemId = R.id.navigation_home
            1 -> bottomNavigationView.selectedItemId = R.id.navigation_classify
            2 -> bottomNavigationView.selectedItemId = R.id.navigation_my
            else -> {
            }
        }
    }

    override fun getDataSuccess(model: MainModel) {
    }

    override fun getDataFail(msg: String) {
    }
}
