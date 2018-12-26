package com.xiaobai.libutils.common

import android.content.Context
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import java.io.File
import java.lang.reflect.Array
import java.lang.reflect.InvocationTargetException

/**
 * SD卡辅助类
 *
 * @author 来自网络 on 2015/9/18.
 */
class StorageCardUtils private constructor() {

    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        /**
         * 判断SDCard是否可用
         *
         * @return 结果
         */
        val isSDCardEnable: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        fun getStoragePath(mContext: Context, isRemove: Boolean): String? {

            val mStorageManager = mContext.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            var storageVolumeClazz: Class<*>? = null
            try {
                storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
                val getVolumeList = mStorageManager.javaClass.getMethod("getVolumeList")
                val getPath = storageVolumeClazz!!.getMethod("getPath")
                val isRemovable = storageVolumeClazz.getMethod("isRemovable")
                val result = getVolumeList.invoke(mStorageManager)
                val length = Array.getLength(result)
                for (i in 0 until length) {
                    val storageVolumeElement = Array.get(result, i)
                    val path = getPath.invoke(storageVolumeElement) as String
                    val removable = isRemovable.invoke(storageVolumeElement) as Boolean
                    if (isRemove == removable) {
                        return path
                    }
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * 获取SD卡路径
         *
         * @return 路径
         */
        val sdCardPath: String
            get() = Environment.getExternalStorageDirectory().absolutePath + File.separator

        /**
         * 获取SD卡的剩余容量 单位byte
         *
         * @return 剩余容量
         */
        // 获取空闲的数据块的数量
        // 获取单个数据块的大小（byte）
        val sdCardAllSize: Long
            get() {
                if (isSDCardEnable) {
                    val stat = StatFs(sdCardPath)
                    val availableBlocks = stat.availableBlocksLong - 4
                    val freeBlocks = stat.availableBlocksLong
                    return freeBlocks * availableBlocks
                }
                return 0
            }

        /**
         * 获取指定路径所在空间的剩余可用容量字节数，单位byte
         *
         * @param filePath 指定路径
         * @return 容量字节 SDCard可用空间，内部存储可用空间
         */
        fun getFreeBytes(filePath: String): Long {

            var filePath = filePath
            // 如果是sd卡的下的路径，则获取sd卡可用容量
            filePath = if (filePath.startsWith(sdCardPath)) {
                sdCardPath
            } else {// 如果是内部存储的路径，则获取内存存储的可用容量
                Environment.getDataDirectory().absolutePath
            }
            val stat = StatFs(filePath)
            val availableBlocks = stat.availableBlocksLong - 4

            return stat.blockSizeLong * availableBlocks
        }

        /**
         * 获取系统存储路径
         *
         * @return 路径
         */
        val rootDirectoryPath: String
            get() = Environment.getRootDirectory().absolutePath

        /**
         * 获取缓存地址，是否有sd卡可用
         * 前者获取到的就是 /sdcard/Android/data/<application package>/cache 这个路径，
         * 而后者获取到的是 /data/data/<application package>/cache 这个路径。
         *
         * @param context    上下文
         * @param uniqueName 存放缓存的唯一标识文件夹名
         * @return 文件
        </application></application> */
        fun getDiskCacheDir(context: Context, uniqueName: String): File {
            val cachePath: String = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                || !Environment.isExternalStorageRemovable()) {

                if (context.externalCacheDir == null) {
                    context.cacheDir.path
                } else {
                    context.externalCacheDir!!.path
                }
            } else {
                context.cacheDir.path
            }
            return File(cachePath + File.separator + uniqueName)
        }
    }
}
