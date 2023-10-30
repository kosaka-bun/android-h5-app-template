package de.honoka.android.h5apptemplate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

@SuppressLint("SetJavaScriptEnabled")
class WebActivity : AppCompatActivity() {

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //解决状态栏白底白字的问题
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_web)
        initWebView()
    }

    private fun initWebView() {
        val webView = findViewById<WebView>(R.id.web_view).apply {
            webViewClient = object : WebViewClient() {

                //重写此方法，解决WebView在重定向时打开系统浏览器的问题
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val url = request.url.toString()
                    //禁止WebView加载未知协议的URL
                    if(!url.startsWith("http")) return true
                    view.loadUrl(url)
                    return true
                }
            }
            settings.run {
                //必须打开，否则网页可能显示为空白
                javaScriptEnabled = true
            }
        }
        webView.run {
            setOnLongClickListener { true }
            isVerticalScrollBarEnabled = true
            scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
            loadUrl("http://localhost:38081/")
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {

            private var lastTimePressBack = 0L

            override fun handleOnBackPressed() {
                if(webView.canGoBack()) {
                    webView.goBack()
                    return
                }
                if(System.currentTimeMillis() - lastTimePressBack > 2500) {
                    Toast.makeText(this@WebActivity, "再进行一次返回退出应用", Toast.LENGTH_SHORT).show()
                    lastTimePressBack = System.currentTimeMillis()
                } else {
                    finish()
                    exitProcess(0)
                }
            }
        })
    }
}