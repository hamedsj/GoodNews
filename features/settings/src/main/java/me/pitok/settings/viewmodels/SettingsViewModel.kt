package me.pitok.settings.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.pitok.androidcore.qulifiers.ApplicationContext
import me.pitok.coroutines.Dispatcher
import me.pitok.lifecycle.SingleLiveData
import me.pitok.settings.R
import me.pitok.navigation.Navigate
import me.pitok.settings.entity.NotifsCount
import me.pitok.settings.entity.UIMode
import me.pitok.settings.views.SettingsFragment
import me.pitok.sharedpreferences.StoreModel
import me.pitok.sharedpreferences.di.qulifiers.SettingsSP
import me.pitok.sharedpreferences.typealiases.SpWriter
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val dispatcher: Dispatcher,
                                            @SettingsSP private val settingsWriter: SpWriter,
                                            @ApplicationContext private val context:Context): ViewModel() {

    companion object{
        const val UI_MODE_KEY = "ui_mode"
        const val DARK_MODE_VALUE = "0"
        const val LIGHT_MODE_VALUE = "1"

        const val NOTIFS_COUNT_KEY = "notifs_count"
        val NOTIFS_SPINNER_ITEMS = arrayOf("بدون اعلان" ,"هر ساعت", "هر ۳ ساعت", "هر ۶ ساعت", "هر ۱۲ ساعت")
        const val NOTIFS_COUNT_NONE = "0"
        const val NOTIFS_COUNT_ONE = "1"
        const val NOTIFS_COUNT_THREE = "3"
        const val NOTIFS_COUNT_SIX = "6"
        const val NOTIFS_COUNT_TWELVE = "12"
        val NOTIFS_SPINNER_ITEM_TO_VALUE = mapOf(
            NOTIFS_SPINNER_ITEMS[0] to NOTIFS_COUNT_NONE,
            NOTIFS_SPINNER_ITEMS[1] to NOTIFS_COUNT_ONE,
            NOTIFS_SPINNER_ITEMS[2] to NOTIFS_COUNT_THREE,
            NOTIFS_SPINNER_ITEMS[3] to NOTIFS_COUNT_SIX,
            NOTIFS_SPINNER_ITEMS[4] to NOTIFS_COUNT_TWELVE
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
        viewModelScope.launch(dispatcher.io) {
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