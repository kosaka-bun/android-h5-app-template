package de.honoka.android.h5apptemplate.jsinterface.definition.async

data class AsyncTaskResult(

    @JvmField
    var isResolve: Boolean? = null,

    @JvmField
    var isPlainText: Boolean? = null,

    var result: String? = null
)