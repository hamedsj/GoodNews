package me.pitok.settings.datasource

import me.pitok.datasource.Readable
import me.pitok.settings.entity.UIMode
import me.pitok.settings.viewmodels.SettingsViewModel
import me.pitok.sharedpreferences.di.qulifiers.SettingsSP
import me.pitok.sharedpreferences.typealiases.SpReader
import javax.inject.Inject

class UISettingReader @Inject constructor(
    @SettingsSP private val spSettings: SpReader
): Readable<UIMode>{
    override fun read(): UIMode {
        return when(spSettings.read(SettingsViewModel.UI_MODE_KEY)){
            SettingsViewModel.LIGHT_MODE_VALUE -> UIMode.LightMode
            else -> UIMode.DarkMode
        }
    }
}