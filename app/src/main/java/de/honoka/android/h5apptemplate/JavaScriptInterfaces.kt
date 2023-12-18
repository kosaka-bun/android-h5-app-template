package de.honoka.android.h5apptemplate

import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast

fun getAllJsInterfaces(webActivity: WebActivity) = arrayOf(
    BasicJsInterface(webActivity)
)

class BasicJsInterface(
    private val webActivity: WebActivity
) {

    @JavascriptInterface
    fun openNewWebActivity(path: String) {
        val url = "http://localhost:${WebServerVariables.serverPort}$path"
        webActivity.run {
            startActivity(Intent(this, WebActivity::class.java).apply {
                putExtra("url", url)
            })
        }
    }

    @JavascriptInterface
    fun test() {
        Toast.makeText(webActivity, "Test", Toast.LENGTH_SHORT).show()
    }
}