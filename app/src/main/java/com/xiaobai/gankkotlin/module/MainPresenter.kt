package com.xiaobai.gankkotlin.module

import com.xiaobai.gankkotlin.base.presenter.ApiPresenter

/**
 * 项目主界面业务处理
 *
 * @author baiyunfei on 2018/11/22.
 */
class MainPresenter : ApiPresenter<MainView>() {

    override fun initModel() {

    }

    fun loadDataByRetrofitRxjava(cityId: String) {

//        iView!!.showLoading()
//
//        addSubscription(apiStores!!.loadDataByRetrofitRxJava(cityId), object : ApiCallback<MainModel>() {
//            override fun onSuccess(model: MainModel) {
//                iView!!.getDataSuccess(model)
//            }
//
//            override fun onFailure(msg: String) {
//                iView!!.getDataFail(msg)
//            }
//
//            override fun onFinish() {
//                iView!!.hideLoading()
//            }
//        })
    }
}
