package me.pitok.addneew.views

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_neews.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.pitok.addneew.R
import me.pitok.addneew.di.builder.AddNeewsComponentBuilder
import me.pitok.addneew.states.AddNeewsViewState
import me.pitok.addneew.viewmodels.AddNeewsViewModel
import me.pitok.coroutines.Dispatcher
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.navigation.Navigate
import me.pitok.navigation.observeNavigation
import me.pitok.neew.NeewAddType
import javax.inject.Inject

class AddNeewsFragment : Fragment(R.layout.fragment_add_neews) {

    companion object{
        const val FLOATING_BUTTON_ANIM_DURATION = 200L
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dispatcher: Dispatcher

    private val addNeewsViewModel: AddNeewsViewModel by viewModels{ viewModelFactory }

    private val navigationObservable = MutableLiveData<Navigate>()

    private var lastViewState: AddNeewsViewState? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AddNeewsComponentBuilder.getComponent().inject(this)
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
        addNeewsSendBt.isEnabled = state.isSendButtonEnabled
        lastViewState?.apply {
            if(state.addNeewType::class != addNeewType::class){
                YoYo.with(Techniques.Shake)
                    .duration(FLOATING_BUTTON_ANIM_DURATION)
                    .repeat(1)
                    .onStart { _ ->
                        lifecycleScope.launch {
                            delay(FLOATING_BUTTON_ANIM_DURATION/2)
                            withContext(dispatcher.main){
                                addNeewsTypeFb.setImageResource(when(state.addNeewType){
                                    is NeewAddType.ByLink -> R.drawable.ic_twitter
                                    is NeewAddType.ByContent -> R.drawable.ic_text
                                })
                            }
                        }
                    }
                    .playOn(addNeewsTypeFb)
            }
        }
    }

    private fun showMessage(message:String){
        Snackbar.make(addNeewsRoot,message,Snackbar.LENGTH_LONG).show()
    }

    private fun onBackClickListener(view: View){
        navigationObservable.value = Navigate.Up
    }
}