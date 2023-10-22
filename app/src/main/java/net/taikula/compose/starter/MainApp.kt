package net.taikula.compose.starter

import android.app.Application

/**
 * @author miaojiaxin
 * @date 2023/10/16
 */
class MainApp : Application() {
    companion object {
        lateinit var context: MainApp
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}