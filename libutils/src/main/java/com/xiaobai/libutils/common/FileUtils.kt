package com.xiaobai.libutils.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.File
import java.net.URLConnection

/**
 * 文件工具类
 *
 * @author 来自网络 on 2016/2/26
 */
object FileUtils {

    /**
     * 打开不同类型的文件
     *
     * @param filePath 文件路径
     * @return intent
     */
    fun getOpenFileIntent(context: Context, filePath: String): Intent {

        val file = File(filePath)

        // 打开
        val intent = Intent()
        // 打开、显示
        intent.action = Intent.ACTION_VIEW
        val index = file.name.lastIndexOf(".")
        val suffix = file.name.substring(index + 1)
        val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix)

        val data: Uri

        val version7 = 24

        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= version7) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            data = FileProvider.getUriForFile(
                context,
                "com.kzcpm.scene.fileprovider", file
            )
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            data = Uri.fromFile(file)
        }

        intent.setDataAndType(data, type)

        return intent
    }

    /**
     * 删除空目录
     *
     * @param dir 将要删除的目录路径
     */
    fun doDeleteEmptyDir(dir: String): Boolean {
        return File(dir).delete()
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list()
            //递归删除目录中的子目录下
            for (aChildren in children) {
                val success = deleteFile(File(dir, aChildren))
                if (!success) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 删除文件
     *
     * @return flag
     */
    fun deleteFile(file: File): Boolean {
        var flag = false
        // 路径为文件且不为空则进行删除
        if (file.isFile && file.exists()) {
            flag = file.delete()
        }
        return flag
    }

    /**
     * 删除文件集合
     */
    fun deleteFileList(fileList: List<File>) {

        for (file in fileList) {
            FileUtils.deleteFile(file)
        }
    }

    /**
     * 修改文件名
     *
     * @param filePath 文件名和路径
     * @param toFile   新的文件名
     */
    fun renameFile(filePath: String, toFile: String) {

        val toBeRenamed = File(filePath)
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory) {
            return
        }

        val newFile = File(toFile)

        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            println("File has been renamed.")
        } else {
            println("Error renmaing file")
        }
    }

    /**
     * 转换文件大小值为string文字类型
     *
     * @param length 文件大小
     * @return 文件大小字符串
     */
    fun getFileSize(length: Long): String {

        val gb = (2 shl 29).toLong()
        val mb = (2 shl 19).toLong()
        val kb = (2 shl 9).toLong()

        return if (length < kb) {
            length.toString() + "B"
        } else if (length < mb) {
            String.format("%.2fKB", length / kb.toDouble())
        } else if (length < gb) {
            String.format("%.2fMB", length / mb.toDouble())
        } else {
            String.format("%.2fGB", length / gb.toDouble())
        }
    }

    /**
     * 判断文件是否是图片
     *
     * @param fileName 文件名
     * @return 是否存在
     */
    fun isImage(fileName: String): Boolean {
        return fileName.contains("jpg") || fileName.contains("JPG") ||
                fileName.contains("jpeg") || fileName.contains("JPEG") ||
                fileName.contains("png") || fileName.contains("PNG") ||
                fileName.contains("gif") || fileName.contains("GIF")
    }

    /**
     * 通过文件名获取拓展名
     *
     * @param fileName 文件名
     * @return 文件拓展名
     */
    fun getExtName(fileName: String): String {

        val lastIndexOfDot = fileName.lastIndexOf(".")

        return if (lastIndexOfDot < 0) {
            ""
        } else fileName.substring(lastIndexOfDot + 1)

    }

    fun getMimeType(fileUrl: String): String {

        var type = ""

        try {
            val fileNameMap = URLConnection.getFileNameMap()
            type = fileNameMap.getContentTypeFor(fileUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return type
    }
}