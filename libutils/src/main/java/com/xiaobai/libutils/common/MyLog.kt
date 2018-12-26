package com.xiaobai.libutils.common

import com.orhanobut.logger.Logger

/**
 * 自定义Log
 *
 * @author 来自网络 on 2015/7/14
 */
object MyLog {

    private const val VERBOSE = 1

    private const val DEBUG = 2

    private const val INFO = 3

    private const val WARN = 4

    private const val ERROR = 5

    private const val NOTHING = 6

    private  var LEVEL = VERBOSE

    fun close(){
        LEVEL = NOTHING
    }

    fun v(tag: String, msg: String) {
        if (LEVEL <= VERBOSE) {
            Logger.v(msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (LEVEL <= DEBUG) {
            Logger.d(msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (LEVEL <= INFO) {
            Logger.i(msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (LEVEL <= WARN) {
            Logger.w(msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (LEVEL <= ERROR) {
            Logger.e(msg)
        }
    }

    fun json(tag: String, msg: String) {
        if (LEVEL <= ERROR) {
            Logger.json(msg)
        }
    }
}
