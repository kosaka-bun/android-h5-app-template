package de.honoka.android.h5apptemplate

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.webkit.MimeTypeMap
import fi.iki.elonen.NanoHTTPD
import java.io.ByteArrayInputStream

class WebServer(private val context: Context) : NanoHTTPD(38081) {

    private val staticResourcesPrefixes = arrayOf(
        "/assets", "/font", "/img", "/js", "/favicon.ico"
    )

    override fun serve(session: IHTTPSession): Response {
        val params = HashMap<String, String>()
        session.parameters.forEach {
            params[it.key] = if(it.value.isNotEmpty()) it.value[0] else ""
        }
        var path = session.uri
        if(path == "/") path = "/index.html"
        return handle(path, params)
    }

    private fun handle(path: String, params: Map<String, String>): Response {
        //判断路径前缀
        staticResourcesPrefixes.forEach {
            //加载静态资源
            if(path.startsWith(it)) return staticResourceResponse(path)
        }
        //加载index.html
        return indexHtmlResponse()
    }

    private fun staticResourceResponse(path: String): Response {
        val content = context.assets.open("web$path").use { it.readBytes() }
        val fileExt = path.substring(path.lastIndexOf(".") + 1)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExt)
        return newFixedLengthResponse(Response.Status.OK, mimeType, ByteArrayInputStream(content), content.size.toLong())
    }

    private fun indexHtmlResponse(): Response {
        val content = context.assets.open("web/index.html").use { it.readBytes() }
        return newFixedLengthResponse(Response.Status.OK, MIME_HTML, ByteArrayInputStream(content), content.size.toLong())
    }
}

class WebServerService : Service() {

    private val webServer = WebServer(this)

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        webServer.start()
    }

    override fun onDestroy() {
        webServer.stop()
    }
}