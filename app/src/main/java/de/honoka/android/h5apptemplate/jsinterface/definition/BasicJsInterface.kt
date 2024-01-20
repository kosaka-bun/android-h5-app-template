package de.honoka.android.h5apptemplate.jsinterface.definition

import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.alibaba.fastjson2.JSONObject
import de.honoka.android.h5apptemplate.jsinterface.async.AsyncJavascriptInterface
import de.honoka.android.h5apptemplate.ui.WebActivity
import de.honoka.android.h5apptemplate.util.WebServerVariables
import java.util.UUID
import java.util.concurrent.TimeUnit

class BasicJsInterface(private val webActivity: WebActivity) {

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
    fun getUuid(): String = UUID.randomUUID().toString()

    @JavascriptInterface
    fun test() {
        Toast.makeText(webActivity, "Test", Toast.LENGTH_SHORT).show()
    }

    @AsyncJavascriptInterface
    @JavascriptInterface
    fun asyncMethodTest(a: String, b: String): JSONObject {
        TimeUnit.SECONDS.sleep(3)
        return JSONObject().fluentPut("sum", (a.toInt() + b.toInt()).toString())
    }
}