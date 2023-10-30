package de.honoka.android.h5apptemplate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenToShow()
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.IO).launch {
            initApplication()
            jumpToWebActivty()
        }
    }

    /**
     * 全屏化当前Activity
     */
    @Suppress("DEPRECATION")
    private fun fullScreenToShow() {
        //隐藏状态栏（手机时间、电量等信息显示的地方）
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //隐藏虚拟按键
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun initApplication() {
        startService(Intent(this, WebServerService::class.java))
    }

    private fun jumpToWebActivty() = runOnUiThread {
        startActivity(Intent(this, WebActivity::class.java))
        finish()
    }
}