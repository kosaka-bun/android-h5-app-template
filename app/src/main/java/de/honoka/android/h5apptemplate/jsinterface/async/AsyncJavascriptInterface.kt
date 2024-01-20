package de.honoka.android.h5apptemplate.jsinterface.async

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AsyncJavascriptInterface(

    val isPlainText: Boolean = false
)
