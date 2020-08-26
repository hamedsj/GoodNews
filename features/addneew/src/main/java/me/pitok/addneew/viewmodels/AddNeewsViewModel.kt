package me.pitok.addneew.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.pitok.addneew.states.AddNeewsViewState
import me.pitok.coroutines.Dispatcher
import me.pitok.lifecycle.update
import me.pitok.neew.NeewAddType
import me.pitok.neew.api.request.AddNeewRequest
import me.pitok.neew.datasource.NeewWritable
import me.pitok.networking.CommonExceptions
import me.pitok.networking.ifSuccessful
import me.pitok.networking.otherwise
import me.pitok.sdkextentions.isValidUrlWithProtocol
import javax.inject.Inject

class AddNeewsViewModel @Inject constructor(private val neewWritable: NeewWritable,
                                            private val dispatcher: Dispatcher): ViewModel() {


    private val pOnViewStateChangedObservable = MutableLiveData<AddNeewsViewState>()
    val onViewStateChangedObservable : LiveData<AddNeewsViewState> = pOnViewStateChangedObservable

    private val pShowMessageLiveData = MutableLiveData<String>()
    val showMessageLiveData : LiveData<String> = pShowMessageLiveData


    private var addType: NeewAddType = NeewAddType.ByContent



    fun onChangeTypeClick(view: View){
        pOnViewStateChangedObservable.update {
            when(addNeewType){
                is NeewAddType.ByLink -> {
                    addType = NeewAddType.ByContent
                    copy(addNeewType = NeewAddType.ByContent)
                }
                is NeewAddType.ByContent -> {
                    addType = NeewAddType.ByLink
                    copy(addNeewType = NeewAddType.ByLink)
                }
            }
        }
    }

    fun onContentChangedListener(content: String){
        pOnViewStateChangedObservable.update {
            when(addNeewType){
                is NeewAddType.ByContent -> {
                        copy(isSendButtonEnabled = (content.trim().length in 1..280))
                }
                is NeewAddType.ByLink -> {
                    if (content.trim().isEmpty()) {
                        copy(isSendButtonEnabled = false)
                    }else{
                        copy(isSendButtonEnabled = content.isValidUrlWithProtocol())
                    }
                }
            }
        }
    }

    fun sendNeews(content: String){
        viewModelScope.launch(dispatcher.io) {
            neewWritable
                .write(AddNeewRequest(content, addType))
                .ifSuccessful {
                    TODO("exit from fragment")
                }.otherwise {
                    pShowMessageLiveData.value = when(it){
                        is CommonExceptions.ConnectionException -> "اینترنت شما قطع می‌باشد =("
                        else -> "ارسال گزارش با خطا روبرو شد =("
                    }
                }
        }
    }
}
