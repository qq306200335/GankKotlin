package com.xiaobai.libutils.authority

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.xiaobai.libutils.R
import java.io.File

/**
 *Android 8.0 后APP安装权限申请界面
 */
class UpdateManagerActivity : AppCompatActivity() {

    //安装包地址
    private var saveFilePath: String? = null
    private var authority: String? = null

    companion object {
        const val SAVE_FILE_PATH: String = "saveFilePath"
        const val AUTHORITY: String = "authority"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_manager)

        saveFilePath = intent.getStringExtra(SAVE_FILE_PATH)
        authority = intent.getStringExtra(AUTHORITY)

        val intent0 = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
        startActivityForResult(intent0, 10086)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            10086 -> {

                val file = File(saveFilePath)

                if (!file.exists()) {
                    return
                }

                val intent = Intent(Intent.ACTION_VIEW)

                val apkUri: Uri
                if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
                    //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                    apkUri = FileProvider.getUriForFile(
                        this, authority!!, file
                    )
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } else {
                    apkUri = Uri.parse("file://$saveFilePath")
                }
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
                startActivity(intent)
            }
            else -> {
            }
        }
    }
}
