package de.honoka.android.h5apptemplate

import android.app.Application
import android.webkit.MimeTypeMap
import fi.iki.elonen.NanoHTTPD
import java.io.ByteArrayInputStream

object WebServerConstants {

    //不同应用使用相同的端口可能导致APP启动闪退，需事先修改此处的监听端口
    const val SERVER_PORT = 38081
}

class WebServer(private val application: Application) : NanoHTTPD(WebServerConstants.SERVER_PORT) {

    companion object {

        lateinit var instance: WebServer

        private val staticResourcesPrefixes = arrayOf(
            "/assets", "/font", "/img", "/js", "/favicon.ico"
        )

        fun checkOrRestartInstance() {
            if(instance.isAlive) return
            instance.start()
        }
    }

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
        val content = application.assets.open("web$path").use { it.readBytes() }
        val fileExt = path.substring(path.lastIndexOf(".") + 1)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExt)
        return newFixedLengthResponse(Response.Status.OK, mimeType, ByteArrayInputStream(content), content.size.toLong())
    }

    private fun indexHtmlResponse(): Response {
        val content = application.assets.open("web/index.html").use { it.readBytes() }
        return newFixedLengthResponse(Response.Status.OK, MIME_HTML, ByteArrayInputStream(content), content.size.toLong())
    }
}