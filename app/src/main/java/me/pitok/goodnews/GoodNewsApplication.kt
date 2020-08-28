package me.pitok.goodnews

import android.app.Application
import me.pitok.androidcore.builder.AndroidCoreComponentBuilder

class GoodNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidCoreComponentBuilder.application = this
    }
}