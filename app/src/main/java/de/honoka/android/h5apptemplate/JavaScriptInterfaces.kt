package de.honoka.android.h5apptemplate

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
    fun test() {
        Toast.makeText(webView.context, "Test", Toast.LENGTH_SHORT).show()
    }
}