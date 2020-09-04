package me.pitok.goodnews

import android.app.Application
import me.pitok.androidcore.builder.AndroidCoreComponentBuilder
import me.pitok.options.di.builder.OptionsComponentBuilder

class GoodNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidCoreComponentBuilder.application = this
        OptionsComponentBuilder.getComponent().exposeStartupTask().run()
    }
}