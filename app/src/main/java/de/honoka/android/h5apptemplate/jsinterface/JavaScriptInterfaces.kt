package de.honoka.android.h5apptemplate.jsinterface

import de.honoka.android.h5apptemplate.jsinterface.async.AsyncTaskJsInterface
import de.honoka.android.h5apptemplate.jsinterface.definition.BasicJsInterface
import de.honoka.android.h5apptemplate.ui.WebActivity

class JavaScriptInterfaces(private val webActivity: WebActivity) {

    private val interfaceInstances = arrayOf(
        BasicJsInterface(webActivity),
        AsyncTaskJsInterface(this, webActivity)
    )

    val interfaces = HashMap<String, Any>().apply {
        interfaceInstances.forEach {
            this[it.javaClass.simpleName] = it
        }
    }

    init {
        registerJsInterfaces()
    }

    private fun registerJsInterfaces() {
        interfaceInstances.forEach {
            webActivity.webView.addJavascriptInterface(it, "android_${it.javaClass.simpleName}")
        }
    }
}