package de.honoka.android.h5appdemo.ui

import de.honoka.android.h5appdemo.jsinterface.TestJsInterface
import de.honoka.sdk.util.android.ui.AbstractWebActivity

class WebActivity : AbstractWebActivity() {

    override val definedJsInterfaceInstances: List<Any> = listOf(TestJsInterface(this))

    override fun extendedOnResume() {}
}
