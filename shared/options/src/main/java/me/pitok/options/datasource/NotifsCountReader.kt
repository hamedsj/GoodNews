package me.pitok.options.datasource

import me.pitok.datasource.Readable
import me.pitok.options.entities.NotifsCount
import me.pitok.sharedpreferences.di.qulifiers.SettingsSP
import me.pitok.sharedpreferences.typealiases.SpReader
import javax.inject.Inject

class NotifsCountReader @Inject constructor(
    @SettingsSP private val spSettings: SpReader
): Readable<NotifsCount>{

    companion object{
        const val NOTIFS_COUNT_KEY = "notifs_count"
        const val NOTIFS_COUNT_NONE_STR = "0"
        const val NOTIFS_COUNT_ONE_STR = "1"
        const val NOTIFS_COUNT_THREE_STR = "3"
        const val NOTIFS_COUNT_SIX_STR = "6"
        const val NOTIFS_COUNT_TWELVE_STR = "12"
    }

    override fun read(): NotifsCount {
        return when(spSettings.read(NOTIFS_COUNT_KEY)){
            NOTIFS_COUNT_ONE_STR -> NotifsCount.EveryHour
            NOTIFS_COUNT_THREE_STR -> NotifsCount.Every3Hours
            NOTIFS_COUNT_SIX_STR -> NotifsCount.Every6Hours
            NOTIFS_COUNT_TWELVE_STR -> NotifsCount.Every12Hours
            else -> NotifsCount.None
        }
    }
}