package de.honoka.android.h5apptemplate.ui

import de.honoka.android.h5apptemplate.jsinterface.TestJsInterface
import de.honoka.sdk.util.android.ui.AbstractWebActivity

class WebActivity : AbstractWebActivity() {

    override val definedJsInterfaceInstances: List<Any> = listOf(TestJsInterface(this))

    override fun extendedOnResume() {}
}
