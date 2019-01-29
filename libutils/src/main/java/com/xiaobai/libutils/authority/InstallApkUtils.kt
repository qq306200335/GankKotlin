package com.xiaobai.libutils.authority

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

/**
 * @author baiyunfei on 2019/1/23
 * email 306200335@qq.com
 */
class InstallApkUtils constructor(
    private val context: Context,
    private val fileProvider: String,
    private val filePath: String
) {

    fun initAuthority() {

        if (Build.VERSION.SDK_INT >= 26) {
            //判断8.0是否允许运行位置软件
            val b = context.packageManager.canRequestPackageInstalls()
            if (b) {
                installApk()
            } else {
                val intent = Intent(context, UpdateManagerActivity::class.java)
                intent.putExtra(UpdateManagerActivity.SAVE_FILE_PATH, filePath)
                intent.putExtra(UpdateManagerActivity.AUTHORITY, fileProvider)
                context.startActivity(intent)
            }
        } else {
            installApk()
        }
    }

    /**
     * 安装下载的apk文件
     */
    private fun installApk() {

        val file = File(filePath)
        if (!file.exists()) {
            return
        }

        val intent = Intent(Intent.ACTION_VIEW)

        val apkUri: Uri
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            apkUri = FileProvider.getUriForFile(context, fileProvider, file)
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            apkUri = Uri.parse("file://$filePath")
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}