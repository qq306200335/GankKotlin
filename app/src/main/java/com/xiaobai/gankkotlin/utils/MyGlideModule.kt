package com.xiaobai.gankkotlin.utils

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

/**
 * 图片加载机制配置
 *
 * @author baiyunfei on 2016/12/14
 */
@GlideModule
class MyGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Apply options to the builder here.

        builder.setLogLevel(Log.ERROR)

        //获取系统分配给应用的总内存大小
        val maxMemory = Runtime.getRuntime().maxMemory().toInt()
        //设置图片内存缓存占用八分之一
        val memoryCacheSize = maxMemory / 8
        //设置内存缓存大小
        builder.setMemoryCache(LruResourceCache(memoryCacheSize.toLong()))

        //指定的是数据的缓存地址
        val cacheDir = context.externalCacheDir
        //最多可以缓存多少字节的数据
        val diskCacheSize = 1024 * 1024 * 300

        //设置磁盘缓存大小
        if (cacheDir != null) {
            builder.setDiskCache(DiskLruCacheFactory(cacheDir.path, "glide", diskCacheSize.toLong()))
        }

        //        也可以通过如下两种方式
        //        存放在data/data/xxxx/cache/
        //        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
        //        存放在外置文件浏览器
        //        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize));

        //设置BitmapPool缓存内存大小
        builder.setBitmapPool(LruBitmapPool(memoryCacheSize.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {}
}
