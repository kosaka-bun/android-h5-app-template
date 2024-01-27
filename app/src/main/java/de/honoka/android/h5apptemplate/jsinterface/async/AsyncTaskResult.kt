package de.honoka.android.h5apptemplate.jsinterface.async

data class AsyncTaskResult(

    @JvmField
    var isResolve: Boolean? = null,

    @JvmField
    var isPlainText: Boolean? = null,

    var message: String? = null,

    var result: String? = null
)