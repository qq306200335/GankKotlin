package com.xiaobai.gankkotlin.model

/**
 * Created by WuXiaolong on 2015/9/23.
 * 业务具体处理，包括负责存储、检索、操纵数据等
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
data class MainModel constructor(val weatherinfo: WeatheringBean) {

    class WeatheringBean {
        var city: String? = null
        var cityid: String? = null
        var temp: String? = null
        var wd: String? = null
        var ws: String? = null
        var sd: String? = null
        var wse: String? = null
        var time: String? = null
        var radar: String? = null
        var njd: String? = null
        var qy: String? = null
    }
}
