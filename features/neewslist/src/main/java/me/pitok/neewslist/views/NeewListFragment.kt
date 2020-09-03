package me.pitok.neewslist.views

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_neews_list.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.pitok.coroutines.Dispatcher
import me.pitok.design.AlertBottomSheetDialog
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.navigation.observeNavigation
import me.pitok.neew.entity.NeewEntity
import me.pitok.neewslist.R
import me.pitok.neewslist.di.builder.NeewsListComponentBuilder
import me.pitok.neewslist.viewmodels.NeewsListViewModel
import javax.inject.Inject

class NeewListFragment : Fragment(R.layout.fragment_neews_list) {

    companion object{
        const val SHOW_ANIMATION_DELAY = 100L
    }

    @Inject
    lateinit var viewModelFactory:  ViewModelFactory

    @Inject
    lateinit var dispatcher: Dispatcher

    private val neewsListViewModel: NeewsListViewModel by viewModels { viewModelFactory }

    private val neewListEpoxyController = NeewListController(::showSendReportDialog)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        NeewsListComponentBuilder.getComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        neewsListRv.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = neewListEpoxyController.adapter
        }
        neewsListViewModel.apply {
            updateNeewsListLiveData.observe(viewLifecycleOwner,::updateNeewList)
            showMessageLiveData.observe(viewLifecycleOwner,::showMessage)
            navigationObserver.observeNavigation(this@NeewListFragment)
        }
        neewsListNewNeew.setOnClickListener(neewsListViewModel::onNewNeewClick)
        neewsListSettingIc.setOnClickListener(neewsListViewModel::onSettingIcClick)
    }

    private fun updateNeewList(neewsList: List<NeewEntity>){
        neewListEpoxyController.items = neewsList
        neewListEpoxyController.requestModelBuild()
    }

    private fun showMessage(message:String){
        Snackbar.make(neewsListRoot,message,Snackbar.LENGTH_LONG).show()
    }

    private fun showSendReportDialog(position: Int){
        AlertBottomSheetDialog(
            requireContext()
        ).apply {
            title = getString(R.string.send_report_dialog_title)
            onOkClickListener = {
                neewsListViewModel.sendReport(position)
                lifecycleScope.launch{
                    delay(SHOW_ANIMATION_DELAY)
                    withContext(dispatcher.main){
                        dismiss()
                    }
                }
            }
            onCancelClickListener = {
                lifecycleScope.launch{
                    delay(SHOW_ANIMATION_DELAY)
                    withContext(dispatcher.main){
                        dismiss()
                    }
                }
            }
            okBtText = "ارسال"
            cancelBtText = "لغو"
        }.run {
            lifecycleScope.launch{
                delay(SHOW_ANIMATION_DELAY)
                withContext(dispatcher.main){
                    show()
                }
            }
        }
    }

}