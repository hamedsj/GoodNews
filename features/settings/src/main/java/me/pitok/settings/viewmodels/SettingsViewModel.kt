package me.pitok.settings.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.pitok.coroutines.Dispatcher
import me.pitok.lifecycle.SingleLiveData
import me.pitok.navigation.Navigate
import me.pitok.options.datasource.NotifsCountReader.Companion.NOTIFS_COUNT_KEY
import me.pitok.options.datasource.NotifsCountReader.Companion.NOTIFS_COUNT_NONE_STR
import me.pitok.options.datasource.NotifsCountReader.Companion.NOTIFS_COUNT_ONE_STR
import me.pitok.options.datasource.NotifsCountReader.Companion.NOTIFS_COUNT_SIX_STR
import me.pitok.options.datasource.NotifsCountReader.Companion.NOTIFS_COUNT_THREE_STR
import me.pitok.options.datasource.NotifsCountReader.Companion.NOTIFS_COUNT_TWELVE_STR
import me.pitok.options.datasource.UIReader.Companion.DARK_MODE_VALUE
import me.pitok.options.datasource.UIReader.Companion.LIGHT_MODE_VALUE
import me.pitok.options.datasource.UIReader.Companion.UI_MODE_KEY
import me.pitok.options.entities.NotifsCount
import me.pitok.options.entities.UIMode
import me.pitok.settings.views.SettingsFragment
import me.pitok.sharedpreferences.StoreModel
import me.pitok.sharedpreferences.di.qulifiers.SettingsSP
import me.pitok.sharedpreferences.typealiases.SpWriter
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val dispatcher: Dispatcher,
    @SettingsSP private val settingsWriter: SpWriter
): ViewModel() {

    companion object{
        val NOTIFS_SPINNER_ITEMS = arrayOf("بدون اعلان" ,"هر ساعت", "هر ۳ ساعت", "هر ۶ ساعت", "هر ۱۲ ساعت")
        val NOTIFS_SPINNER_ITEM_TO_VALUE = mapOf(
            NOTIFS_SPINNER_ITEMS[0] to NOTIFS_COUNT_NONE_STR,
            NOTIFS_SPINNER_ITEMS[1] to NOTIFS_COUNT_ONE_STR,
            NOTIFS_SPINNER_ITEMS[2] to NOTIFS_COUNT_THREE_STR,
            NOTIFS_SPINNER_ITEMS[3] to NOTIFS_COUNT_SIX_STR,
            NOTIFS_SPINNER_ITEMS[4] to NOTIFS_COUNT_TWELVE_STR
        )
        val NOTIFS_COUNT_TO_SPINNER_POSITION = mapOf(
            NotifsCount.None to 0,
            NotifsCount.EveryHour to 1,
            NotifsCount.Every3Hours to 2,
            NotifsCount.Every6Hours to 3,
            NotifsCount.Every12Hours to 4
        )

    }

    private val pNavigationObservable = SingleLiveData<Navigate>()
    val navigationObservable : LiveData<Navigate> = pNavigationObservable

    fun onBackClick(view: View){
        GlobalScope.launch(dispatcher.main) {
            delay(SettingsFragment.CLICK_ANIMATION_DURATION)
            pNavigationObservable.value = Navigate.Up
        }
    }

    fun onDarkModeClick(darkMode: Boolean){
        GlobalScope.launch(dispatcher.io) {
            settingsWriter.write(
                StoreModel(
                    UI_MODE_KEY,
                    if (darkMode) DARK_MODE_VALUE else LIGHT_MODE_VALUE
                )
            )
            UIMode.currentUIMode = if (!darkMode) UIMode.LightMode else UIMode.DarkMode
            delay(SettingsFragment.VIEW_RECREATE_DURATION)
            withContext(dispatcher.main) {
                pNavigationObservable.value = Navigate.Recreate
            }
        }
    }

    fun onNotifsCountItemSelected(position: Int){
        GlobalScope.launch(dispatcher.io) {
            delay(SettingsFragment.CLICK_ANIMATION_DURATION)
            settingsWriter.write(
                StoreModel(
                    NOTIFS_COUNT_KEY,
                    requireNotNull(NOTIFS_SPINNER_ITEM_TO_VALUE[NOTIFS_SPINNER_ITEMS[position]])
                )
            )
        }
    }


}