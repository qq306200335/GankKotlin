package com.xiaobai.libutils.cache

import android.content.Context
import com.xiaobai.libutils.common.StorageCardUtils
import com.xiaobai.libutils.common.VersionUtils

import java.io.*

/**
 * @author baiyunfei on 2015/9/18
 * email 306200335@qq.com
 */

object DiskLruCacheHelper {

    private var mCache: DiskLruCache? = null

    /**
     * 获取缓存的大小
     *
     * @return 缓存的大小
     */
    val cacheSize: Long
        get() = mCache!!.size()

    /**
     * 打开DiskLruCache
     */
    fun openCache(context: Context) {

        try {
            val cacheDir = StorageCardUtils.getDiskCacheDir(context, "object")
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            mCache = DiskLruCache.open(cacheDir, VersionUtils.getVersionCode(context), 1, (10 * 1024 * 1024).toLong())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 关闭DiskLruCache
     */
    fun close() {

        if (mCache == null || mCache!!.isClosed) {
            return
        }

        try {
            mCache!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 写入缓存
     */
    @Throws(IOException::class)
    fun write(`object`: Any, keyCache: String) {

        if (mCache == null) {
            throw IllegalStateException("Must call openCache() first!")
        }

        val editor = mCache!!.edit(keyCache)

        val outputStream = editor.newOutputStream(0)

        val objectOutputStream = ObjectOutputStream(outputStream)
        objectOutputStream.writeObject(`object`)
        objectOutputStream.close()

        if (outputStream != null) {
            editor.commit()
        } else {
            editor.abort()
        }

        mCache!!.flush()
    }

    /**
     * 写入缓存
     */
    @Throws(IOException::class)
    fun writeInputStream(inputStream: InputStream, keyCache: String) {

        if (mCache == null) {
            throw IllegalStateException("Must call openCache() first!")
        }

        val mEditor = mCache!!.edit(keyCache)

        val outputStream = mEditor.newOutputStream(0)

        val bin = BufferedInputStream(inputStream)
        val bout = BufferedOutputStream(outputStream)

        val buf = ByteArray(1024)
        var len: Int
        while ((bin.read(buf)) != -1) {
            len = bin.read(buf)
            bout.write(buf, 0, len)
        }

        bout.close()
        outputStream.close()

        mEditor.commit()
    }

    /**
     * 读取缓存
     */
    @Throws(IOException::class)
    fun read(keyCache: String): InputStream? {

        if (mCache == null) {
            throw IllegalStateException("Must call openCache() first!")
        }

        val snapshot = mCache!!.get(keyCache)

        return snapshot?.getInputStream(0)
    }

    /**
     * 删除全部缓存
     */
    @Throws(IOException::class)
    fun deleteCache() {
        mCache!!.delete()
    }

    /**
     * 同步日志
     */
    fun syncLog() {
        try {
            mCache!!.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

