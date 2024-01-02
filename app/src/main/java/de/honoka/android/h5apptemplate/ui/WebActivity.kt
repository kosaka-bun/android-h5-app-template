package de.honoka.android.h5apptemplate.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import de.honoka.android.h5apptemplate.util.JavaScriptInterfaces
import de.honoka.android.h5apptemplate.util.WebServer
import de.honoka.android.h5apptemplate.util.WebServerVariables
import kotlin.system.exitProcess

@SuppressLint("SetJavaScriptEnabled")
class WebActivity : AppCompatActivity() {

    private lateinit var url: String

    /**
     * 是否是该应用当中第一个被开启的WebActivity
     */
    private var firstWebActivity: Boolean = false

    lateinit var webView: WebView

    private val webViewClient = object : WebViewClient() {

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

    private val webChromeClient = object : WebChromeClient() {

        override fun onShowCustomView(view: View, callback: CustomViewCallback) {
            fullScreenView?.run {
                callback.onCustomViewHidden()
                return
            }
            val decorView = window.decorView as FrameLayout
            decorView.addView(view, fullScreenViewParams)
            showStatusBar(false)
            fullScreenView = view
            fullScreenViewCallBack = callback
        }

        override fun onHideCustomView() {
            fullScreenView ?: return
            showStatusBar()
            val decorView = window.decorView as FrameLayout
            decorView.removeView(fullScreenView)
            fullScreenView = null
            fullScreenViewCallBack?.onCustomViewHidden()
            webView.visibility = View.VISIBLE
        }
    }

    private var fullScreenView: View? = null

    private val fullScreenViewParams = FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    private var fullScreenViewCallBack: WebChromeClient.CustomViewCallback? = null

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {

        private var lastTimePressBack = 0L

        override fun handleOnBackPressed() {
            if(webView.canGoBack()) {
                webView.goBack()
                return
            }
            if(!firstWebActivity) {
                finish()
                return
            }
            if(System.currentTimeMillis() - lastTimePressBack > 2500) {
                Toast.makeText(this@WebActivity, "再进行一次返回退出应用", Toast.LENGTH_SHORT).show()
                lastTimePressBack = System.currentTimeMillis()
            } else {
                finish()
                if(firstWebActivity) exitProcess(0)
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //解决状态栏白底白字的问题
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_web)
        initActivityParams()
        initWebView()
    }

    override fun onResume() {
        WebServer.checkOrRestartInstance()
        super.onResume()
    }

    private fun initActivityParams() {
        url = intent.getStringExtra("url") ?: "http://localhost:${WebServerVariables.serverPort}/"
        firstWebActivity = intent.getBooleanExtra("firstWebActivity", false)
    }

    private fun initWebView() {
        webView = findViewById(R.id.web_view)
        webView.apply {
            webViewClient = this@WebActivity.webViewClient
            webChromeClient = this@WebActivity.webChromeClient
            settings.run {
                //必须打开，否则网页可能显示为空白
                javaScriptEnabled = true
            }
            //禁用默认长按监听器
            setOnLongClickListener { true }
            isVerticalScrollBarEnabled = false
            scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
            registerJsInterface()
            loadUrl(this@WebActivity.url)
        }
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    @SuppressLint("JavascriptInterface")
    private fun registerJsInterface() {
        JavaScriptInterfaces.newAll(this).forEach {
            webView.addJavascriptInterface(it, "android_${it.javaClass.simpleName}")
        }
    }

    @Suppress("DEPRECATION")
    private fun showStatusBar(show: Boolean = true) {
        val flag = if(show) 0 else WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}