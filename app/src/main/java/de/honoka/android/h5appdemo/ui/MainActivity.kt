package de.honoka.android.h5appdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.honoka.android.h5appdemo.R
import de.honoka.sdk.util.android.basic.GlobalComponents
import de.honoka.sdk.util.android.basic.launchOnIo
import de.honoka.sdk.util.android.server.HttpServer
import de.honoka.sdk.util.android.ui.fullScreenToShow
import de.honoka.sdk.util.android.ui.startRootWebActivty

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenToShow()
        setContentView(R.layout.activity_main)
        GlobalComponents.init(application)
        launchOnIo {
            //init可能是一个耗时的操作，故在IO线程中执行，防止阻塞UI线程
            initApplication()
            startRootWebActivty(WebActivity::class)
        }
    }

    private fun initApplication() {
        HttpServer.start()
    }
}
