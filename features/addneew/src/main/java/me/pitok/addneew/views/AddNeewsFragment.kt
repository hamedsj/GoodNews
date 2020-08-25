package me.pitok.addneew.views

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_neews.*
import me.pitok.addneew.R
import me.pitok.addneew.states.AddNeewsViewState
import me.pitok.addneew.viewmodels.AddNeewsViewModel
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.navigation.Navigate
import me.pitok.navigation.observeNavigation
import javax.inject.Inject

class AddNeewsFragment : Fragment(R.layout.fragment_add_neews) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val addNeewsViewModel: AddNeewsViewModel by viewModels{ viewModelFactory }

    private val navigationObservable = MutableLiveData<Navigate>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //todo: inject here
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationObservable.observeNavigation(this)
        addNeewsContentEt.addTextChangedListener(afterTextChanged = {
            addNeewsViewModel.onContentChangedListener(it.toString())
        })
        addNeewsTypeFb.setOnClickListener(addNeewsViewModel::onChangeTypeClick)
        addNeewsBackIc.setOnClickListener(::onBackClickListener)
        addNeewsSendBt.setOnClickListener{
            it.isEnabled = false
            addNeewsViewModel.sendNeews(addNeewsContentEt.text.toString().trim())
        }
        addNeewsViewModel.apply {
            onViewStateChangedObservable.observe(viewLifecycleOwner,::updateState)
            showMessageLiveData.observe(viewLifecycleOwner,::showMessage)
        }

    }

    private fun updateState(state: AddNeewsViewState){

    }

    private fun showMessage(message:String){
        Snackbar.make(addNeewsRoot,message,Snackbar.LENGTH_LONG).show()
    }

    private fun onBackClickListener(view: View){
        navigationObservable.value = Navigate.Up
    }
}