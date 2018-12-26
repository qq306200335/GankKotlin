package com.xiaobai.gankkotlin.model

import java.io.Serializable

/**
 * @author baiyunfei on 2018/12/6
 * email 306200335@qq.com
 */
class DataInfo : Base(), Serializable {
    var _id = ""
    var content = ""
    var created_at = ""
    var publishedAt = ""
    var updated_at = ""
    var rand_id = ""
    var title = ""
    var imageUrl = ""
}