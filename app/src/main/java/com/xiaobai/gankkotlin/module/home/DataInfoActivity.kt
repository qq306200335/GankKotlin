package com.xiaobai.gankkotlin.module.home

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xiaobai.gankkotlin.R
import com.xiaobai.gankkotlin.model.DataInfo
import kotlinx.android.synthetic.main.activity_data_info.*

class DataInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_info)

        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initView() {

        val webView: WebView = findViewById(R.id.webview)
        val progressBar: ProgressBar = findViewById(R.id.progress_pb)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        /*
          WebView默认用系统自带浏览器处理页面跳转。
          为了让页面跳转在当前WebView中进行，重写WebViewClient。
          但是按BACK键时，不会返回跳转前的页面，而是退出本Activity。重写onKeyDown()方法来解决此问题。
         */
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                //使用当前WebView处理跳转
                view!!.loadUrl(request!!.url.toString())

                return true
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                handler!!.proceed()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Toast.makeText(this@DataInfoActivity, "网页加载出错了，请点击刷新", Toast.LENGTH_SHORT).show()
            }
        }

        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                val maxProgress = 100
                if (newProgress == maxProgress) {
                    //加载完网页进度条消失
                    progressBar.visibility = View.GONE
                } else {
                    //设置进度值
                    progressBar.progress = newProgress
                    //开始加载网页时显示进度条
                    progressBar.visibility = View.VISIBLE
                }
            }
        }

        val dataInfo:DataInfo = intent.getSerializableExtra("dataInfo") as DataInfo

        webview.loadUrl(dataInfo.content)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //处理WebView跳转返回
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
