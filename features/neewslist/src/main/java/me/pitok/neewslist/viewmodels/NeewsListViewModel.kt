package me.pitok.neewslist.viewmodels

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.pitok.androidcore.qulifiers.ApplicationContext
import me.pitok.coroutines.Dispatcher
import me.pitok.lifecycle.SingleLiveData
import me.pitok.navigation.Navigate
import me.pitok.neew.api.request.ReportRequest
import me.pitok.neew.entity.NeewEntity
import me.pitok.neew.repository.NeewsRepository
import me.pitok.neewslist.R
import me.pitok.neewslist.views.NeewListFragment
import me.pitok.networking.CommonExceptions
import me.pitok.networking.ifSuccessful
import me.pitok.networking.otherwise
import java.util.*
import javax.inject.Inject

class NeewsListViewModel @Inject constructor(
    private val neewsRepository: NeewsRepository,
    private val dispatcher: Dispatcher,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val pUpdateNeewsListLiveData = MutableLiveData<List<NeewEntity>>()
    val updateNeewsListLiveData : LiveData<List<NeewEntity>> = pUpdateNeewsListLiveData

    private val pShowMessageLiveData = MutableLiveData<String>()
    val showMessageLiveData : LiveData<String> = pShowMessageLiveData

    private val pNavigationObservable = SingleLiveData<Navigate>()
    val navigationObserver: LiveData<Navigate> = pNavigationObservable

    private var neews: MutableList<NeewEntity> = mutableListOf()

    init {
        fetchNeews()
    }

    private fun fetchNeews(){
        viewModelScope.launch(dispatcher.io){
            for (i in 0..20) {
                neews.add(
                    NeewEntity(
                        1,
                        "loremloremloremloremloremloremloremloremloremloremloremloremloremloremlorem",
                        Date(2020, 5, 20)
                    )
                )
            }
            withContext(dispatcher.main) {
                pUpdateNeewsListLiveData.value = neews
            }
//            neewsRepository.getNeews().ifSuccessful {
//                neews.addAll(it)
//                withContext(dispatcher.main) {
//                    pUpdateNeewsListLiveData.value = neews
//                }
//            }.otherwise {
//                withContext(dispatcher.main) {
//                    pShowMessageLiveData.value = when (it) {
//                        is CommonExceptions.ConnectionException -> "اینترنت شما قطع می‌باشد =("
//                        else -> "ارسال گزارش با خطا روبرو شد =("
//                    }
//                }
//            }
        }
    }


    fun sendReport(position: Int){
        viewModelScope.launch(dispatcher.io) {
            val sendReportResult = neewsRepository.reportNeew(ReportRequest(position))
            withContext(dispatcher.main) {
                sendReportResult.ifSuccessful {
                    pShowMessageLiveData.value = "گزارش با موفقیت ارسال شد =)"
                }.otherwise {
                    pShowMessageLiveData.value = when (it) {
                        is CommonExceptions.ConnectionException -> "اینترنت شما قطع می‌باشد =("
                        else -> "ارسال گزارش با خطا روبرو شد =("
                    }
                }
            }
        }
    }

    fun onNewNeewClick(view: View){
        GlobalScope.launch(dispatcher.default) {
            delay(NeewListFragment.SHOW_ANIMATION_DELAY)
            withContext(dispatcher.main) {
                pNavigationObservable.value =
                    Navigate.ToDeepLink(context.getString(R.string.deeplink_add_news))
            }
        }
    }

    fun onSettingIcClick(view: View){
        GlobalScope.launch(dispatcher.default) {
            delay(NeewListFragment.SHOW_ANIMATION_DELAY)
            withContext(dispatcher.main) {
                pNavigationObservable.value =
                    Navigate.ToDeepLink(context.getString(R.string.deeplink_settings))
            }
        }
    }

}