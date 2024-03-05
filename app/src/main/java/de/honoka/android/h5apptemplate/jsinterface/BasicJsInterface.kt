package de.honoka.android.h5apptemplate.jsinterface

import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast
import cn.hutool.json.JSONObject
import de.honoka.android.h5apptemplate.ui.WebActivity
import de.honoka.sdk.util.android.jsinterface.async.AsyncJavascriptInterface
import de.honoka.sdk.util.android.server.HttpServerVariables
import java.util.*
import java.util.concurrent.TimeUnit

class BasicJsInterface(private val webActivity: WebActivity) {

    @JavascriptInterface
    fun openNewWebActivity(path: String) {
        val url = "http://localhost:${HttpServerVariables.serverPort}$path"
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
    fun asyncMethodTest(a: Int, b: Int): JSONObject {
        TimeUnit.SECONDS.sleep(3)
        return JSONObject().set("sum", (a + b).toString())
    }
}