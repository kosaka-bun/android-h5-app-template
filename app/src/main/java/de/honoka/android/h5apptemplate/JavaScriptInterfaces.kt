package de.honoka.android.h5apptemplate

import android.content.Intent
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast

fun getAllJsInterfaces(webView: WebView) = arrayOf(
    BasicJsInterface(webView)
)

class BasicJsInterface(
    private val webView: WebView
) {

    @JavascriptInterface
    fun openNewWebActivity(path: String) {
        val url = "http://localhost:${WebServerVariables.serverPort}$path"
        webView.context.run {
            startActivity(Intent(this, WebActivity::class.java).apply {
                putExtra("url", url)
            })
        }
    }

    @JavascriptInterface
    fun test() {
        Toast.makeText(webView.context, "Test", Toast.LENGTH_SHORT).show()
    }
}