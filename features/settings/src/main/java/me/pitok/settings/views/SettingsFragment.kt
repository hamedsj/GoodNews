package me.pitok.settings.views

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.pitok.coroutines.Dispatcher
import me.pitok.datasource.Readable
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.navigation.observeNavigation
import me.pitok.settings.R
import me.pitok.settings.di.builder.SettingsComponentBuilder
import me.pitok.settings.entity.NotifsCount
import me.pitok.settings.entity.UIMode
import me.pitok.settings.viewmodels.SettingsViewModel
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    companion object{
        const val CLICK_ANIMATION_DURATION = 100L
        const val VIEW_RECREATE_DURATION = 200L
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var notifsCountReader: Readable<NotifsCount>

    @Inject
    lateinit var uiModeReader: Readable<UIMode>

    private val settingsViewModel: SettingsViewModel by viewModels{ viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        SettingsComponentBuilder.getComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNotifsCountAdapter(SettingsViewModel.NOTIFS_SPINNER_ITEMS)
        settingsViewModel.navigationObservable.observeNavigation(this@SettingsFragment)
        settingsBackIc.setOnClickListener(settingsViewModel::onBackClick)
        settingsDarkModeClick.setOnClickListener{
            settingsDarkModeSw.isChecked = settingsDarkModeSw.isChecked.not()
            settingsViewModel.onDarkModeClick(settingsDarkModeSw.isChecked)
        }
        settingsNotifsCountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                settingsViewModel.onNotifsCountItemSelected(position)
            }
        }

        lifecycleScope.launch(dispatcher.io){
            val count = notifsCountReader.read()
            val uiMode = uiModeReader.read()
            withContext(dispatcher.main){
                settingsDarkModeSw.isChecked = (uiMode is UIMode.LightMode).not()
                settingsNotifsCountSpinner.setSelection(
                    requireNotNull(
                        SettingsViewModel.NOTIFS_COUNT_TO_SPINNER_POSITION[count]
                    )
                )
            }
        }

    }

    private fun setNotifsCountAdapter(items: Array<String>) {
        val adapter: ArrayAdapter<String?> = ArrayAdapter(
            requireActivity(),
            R.layout.item_persian_text_spinner,
            items)
        settingsNotifsCountSpinner.setPopupBackgroundResource(R.drawable.spinner_popup_bg)
        settingsNotifsCountSpinner.adapter = adapter
    }

}