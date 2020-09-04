package me.pitok.options.datasource

import me.pitok.datasource.Readable
import me.pitok.options.entities.UIMode
import me.pitok.sharedpreferences.di.qulifiers.SettingsSP
import me.pitok.sharedpreferences.typealiases.SpReader
import javax.inject.Inject

class UIReader @Inject constructor(
    @SettingsSP private val spSettings: SpReader
): Readable<UIMode>{

    companion object{
        const val UI_MODE_KEY = "ui_mode"
        const val DARK_MODE_VALUE = "0"
        const val LIGHT_MODE_VALUE = "1"
    }

    override fun read(): UIMode {
        return when(spSettings.read(UI_MODE_KEY)){
            LIGHT_MODE_VALUE -> UIMode.LightMode
            else -> UIMode.DarkMode
        }
    }
}