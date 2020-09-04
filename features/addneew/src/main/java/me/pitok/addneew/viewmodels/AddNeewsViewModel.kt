package me.pitok.addneew.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.pitok.addneew.R
import me.pitok.addneew.states.AddNeewsViewState
import me.pitok.addneew.views.AddNeewsFragment
import me.pitok.androidcore.qulifiers.ApplicationContext
import me.pitok.coroutines.Dispatcher
import me.pitok.lifecycle.SingleLiveData
import me.pitok.lifecycle.update
import me.pitok.navigation.Navigate
import me.pitok.neew.NeewAddType
import me.pitok.neew.api.request.AddNeewRequest
import me.pitok.neew.datasource.NeewWritable
import me.pitok.networking.CommonExceptions
import me.pitok.networking.ifSuccessful
import me.pitok.networking.otherwise
import me.pitok.sdkextentions.isValidUrlWithProtocol
import javax.inject.Inject

class AddNeewsViewModel @Inject constructor(private val neewWritable: NeewWritable,
                                            private val dispatcher: Dispatcher,
                                            @ApplicationContext private val context:Context): ViewModel() {


    companion object{
        const val ENTER_LINK_HINT = "لینک توییت را وارد کنید"
        const val ENTER_CONTENT_HINT = "خب دیگه چه خبر؟"
    }


    private val pOnViewStateChangedObservable = MutableLiveData<AddNeewsViewState>(AddNeewsViewState())
    val onViewStateChangedObservable : LiveData<AddNeewsViewState> = pOnViewStateChangedObservable

    private val pShowMessageLiveData = MutableLiveData<String>()
    val showMessageLiveData : LiveData<String> = pShowMessageLiveData

    private val pNavigationObservable = SingleLiveData<Navigate>()
    val navigationObservable : LiveData<Navigate> = pNavigationObservable

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
                        copy(
                            isSendButtonEnabled = (content.trim().length in 1..280),
                            lastContentEntered =  content
                        )
                }
                is NeewAddType.ByLink -> {
                    if (content.trim().isEmpty()) {
                        copy(
                            isSendButtonEnabled = false,
                            lastLinkEntered = content
                        )
                    }else{
                        copy(
                            isSendButtonEnabled = content.isValidUrlWithProtocol(),
                            lastLinkEntered = content
                        )
                    }
                }
            }
        }
    }

    fun sendNeews(content: String){
        GlobalScope.launch(dispatcher.io) {
            neewWritable
                .write(AddNeewRequest(content, addType))
                .ifSuccessful {
                    pNavigationObservable.value = Navigate.ToDeepLink(context.getString(R.string.deeplink_home))
                }.otherwise {
                    pShowMessageLiveData.value = when(it){
                        is CommonExceptions.ConnectionException -> "اینترنت شما قطع می‌باشد =("
                        else -> "ارسال گزارش با خطا روبرو شد =("
                    }
                }
        }
    }

    fun onBackClickListener(view: View){
        GlobalScope.launch(dispatcher.main) {
            delay(AddNeewsFragment.CLICK_ANIMATION_DURATION)
            pNavigationObservable.value = Navigate.Up
        }
    }

}
