package me.pitok.settings.datasource

import me.pitok.datasource.Readable
import me.pitok.settings.entity.NotifsCount
import me.pitok.settings.entity.UIMode
import me.pitok.settings.viewmodels.SettingsViewModel
import me.pitok.sharedpreferences.di.qulifiers.SettingsSP
import me.pitok.sharedpreferences.typealiases.SpReader
import javax.inject.Inject

class NotifsCountSettingReader @Inject constructor(
    @SettingsSP private val spSettings: SpReader
): Readable<NotifsCount>{
    override fun read(): NotifsCount {
        return when(spSettings.read(SettingsViewModel.NOTIFS_COUNT_KEY)){
            SettingsViewModel.NOTIFS_COUNT_ONE -> NotifsCount.EveryHour
            SettingsViewModel.NOTIFS_COUNT_THREE -> NotifsCount.Every3Hours
            SettingsViewModel.NOTIFS_COUNT_SIX -> NotifsCount.Every6Hours
            SettingsViewModel.NOTIFS_COUNT_TWELVE -> NotifsCount.Every12Hours
            else -> NotifsCount.None
        }
    }
}