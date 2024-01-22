package de.honoka.android.h5apptemplate.util

import android.app.Application
import android.webkit.MimeTypeMap
import cn.hutool.core.exceptions.ExceptionUtil
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.JSONWriter
import fi.iki.elonen.NanoHTTPD
import java.io.ByteArrayInputStream
import java.io.File

object WebServerVariables {

    var serverPort = 38081
}

@Suppress("CanBeParameter")
class WebServer(
    private val application: Application,
    private val port: Int = WebServerVariables.serverPort
) : NanoHTTPD(port) {

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
        var path = session.uri
        if(path == "/") path = "/index.html"
        return try {
            handle(path)
        } catch(t: Throwable) {
            errorResponse(t)
        }
    }

    private fun handle(urlPath: String): Response {
        //判断路径是否匹配静态资源前缀
        staticResourcesPrefixes.forEach {
            //加载静态资源
            if(urlPath.startsWith(it)) return staticResourceResponse(urlPath)
        }
        androidImageResponse(urlPath)?.let { return it }
        //加载index.html
        return indexHtmlResponse()
    }

    private fun buildStaticResponse(urlPath: String, content: ByteArray): Response {
        val fileExt = urlPath.substring(urlPath.lastIndexOf(".") + 1)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExt)
        return newFixedLengthResponse(Response.Status.OK, mimeType, ByteArrayInputStream(content), content.size.toLong())
    }

    private fun staticResourceResponse(urlPath: String): Response {
        val content = application.assets.open("web$urlPath").use { it.readBytes() }
        return buildStaticResponse(urlPath, content)
    }

    private fun indexHtmlResponse(): Response {
        val content = application.assets.open("web/index.html").use { it.readBytes() }
        return newFixedLengthResponse(Response.Status.OK, MIME_HTML, ByteArrayInputStream(content), content.size.toLong())
    }

    private fun androidImageResponse(urlPath: String): Response? {
        val prefix = "/android/img"
        if(!urlPath.startsWith(prefix)) return null
        val filePath = "${application.dataDir}${urlPath.substring(prefix.length)}"
        val file = File(filePath)
        if(!file.exists()) return notFoundResponse(filePath)
        return buildStaticResponse(urlPath, file.readBytes())
    }

    private fun notFoundResponse(resourcePath: String): Response = newFixedLengthResponse(
        Response.Status.NOT_FOUND,
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("json"),
        JSON.toJSONString(ApiResponse<Any>().apply {
            code = Response.Status.NOT_FOUND.requestStatus
            msg = "$resourcePath is not found"
        }, JSONWriter.Feature.PrettyFormat)
    )

    private fun errorResponse(t: Throwable): Response = newFixedLengthResponse(
        Response.Status.INTERNAL_ERROR,
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("json"),
        JSON.toJSONString(ApiResponse<Any>().apply {
            code = Response.Status.INTERNAL_ERROR.requestStatus
            msg = t.message
            data = JSONObject().also {
                it["stackTrace"] = ExceptionUtil.stacktraceToString(t)
            }
        }, JSONWriter.Feature.PrettyFormat)
    )
}

data class ApiResponse<T>(

    var code: Int? = null,

    var msg: String? = null,

    var data: T? = null
)