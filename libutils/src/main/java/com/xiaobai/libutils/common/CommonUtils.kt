package com.xiaobai.libutils.common

import android.text.TextUtils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 公共的工具类
 *
 * @author baiyunfei on 2017/11/17
 * email 306200335@qq.com
 */
object CommonUtils {

    /**
     * MD5加密
     *
     * @param key 加密的key
     * @return 加密后的值
     */
    fun hashKeyForDisk(key: String): String {

        val cacheKey: String

        cacheKey = try {
            val mDigest = MessageDigest.getInstance("MD5")
            mDigest.update(key.toByteArray())
            bytesToHexString(mDigest.digest())
        } catch (e: NoSuchAlgorithmException) {
            key.hashCode().toString()
        }

        return cacheKey
    }

    /**
     * @param bytes 字节数据
     * @return 转换后的字符串
     */
    private fun bytesToHexString(bytes: ByteArray): String {

        val sb = StringBuilder()
        var i = 0

        while (i < bytes.size) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
            i++
        }
        return sb.toString()
    }

    /**
     * 验证手机格式
     */
    fun isMobileNumber(mobiles: String): Boolean {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

        val telRegex = "[1][123456789]\\d{9}"

        return if (TextUtils.isEmpty(mobiles)) {
            false
        } else {
            mobiles.matches(telRegex.toRegex())
        }
    }
}
