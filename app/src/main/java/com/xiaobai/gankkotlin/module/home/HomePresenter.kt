package com.xiaobai.gankkotlin.module.home

import com.xiaobai.gankkotlin.base.presenter.ApiPresenter
import com.xiaobai.gankkotlin.http.ApiCallback
import com.xiaobai.gankkotlin.model.DataInfo
import com.xiaobai.gankkotlin.model.HomeModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


/**
 * @author baiyunfei on 2018/12/6
 * email 306200335@qq.com
 */
class HomePresenter : ApiPresenter<HomeContract.HomeView>() {

    val dataInfoList: MutableList<DataInfo> = mutableListOf()

    var number: Int = 1

    override fun initModel() {
    }

    fun getGankListRequest() {

        addSubscription(apiStores!!.getGankList(number.toString()), object : ApiCallback<HomeModel>() {
            override fun onSuccess(model: HomeModel) {

                iView!!.setRefreshComplete()

                for (dataInfo in model.results) {
                    //解析图片地址
                    val doc: Document = Jsoup.parse(dataInfo.content)
                    val images: Elements = doc.select("img")
                    val imageList: MutableList<String> = images[0].toString().split("\"") as MutableList<String>
                    dataInfo.imageUrl = imageList[1]
                    dataInfoList.add(dataInfo)
                }

                iView!!.refreshAdapter()
            }

            override fun onFailure(msg: String) {
                iView!!.showError(msg)
            }

            override fun onFinish() {
            }

        })
    }

}