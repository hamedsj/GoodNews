package me.pitok.goodnews

import android.app.Application
import me.pitok.androidcore.builder.AndroidCoreComponentBuilder
import me.pitok.settings.di.builder.SettingsComponentBuilder

class GoodNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidCoreComponentBuilder.application = this
        SettingsComponentBuilder.getComponent().provideStartupTask().run()
    }
}