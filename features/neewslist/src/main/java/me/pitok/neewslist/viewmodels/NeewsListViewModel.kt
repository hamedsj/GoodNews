package me.pitok.neewslist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.pitok.coroutines.Dispatcher
import me.pitok.neew.api.request.ReportRequest
import me.pitok.neew.entity.NeewEntity
import me.pitok.neew.repository.NeewsRepository
import me.pitok.networking.CommonExceptions
import me.pitok.networking.ifSuccessful
import me.pitok.networking.otherwise
import javax.inject.Inject

class NeewsListViewModel @Inject constructor(
    private val neewsRepository: NeewsRepository,
    private val dispatcher: Dispatcher
): ViewModel() {

    private val pUpdateNeewsListLiveData = MutableLiveData<List<NeewEntity>>()
    val updateNeewsListLiveData : LiveData<List<NeewEntity>> = pUpdateNeewsListLiveData

    private val pShowMessageLiveData = MutableLiveData<String>()
    val showMessageLiveData : LiveData<String> = pShowMessageLiveData

    private var neews: MutableList<NeewEntity> = mutableListOf()

    init {
        fetchNeews()
    }

    private fun fetchNeews(){
        viewModelScope.launch(dispatcher.io){
            neewsRepository.getNeews().ifSuccessful {
                neews.addAll(it)
                pUpdateNeewsListLiveData.value = neews
            }.otherwise {
                pShowMessageLiveData.value = when(it){
                    is CommonExceptions.ConnectionException -> "اینترنت شما قطع می‌باشد =("
                    else -> "ارسال گزارش با خطا روبرو شد =("
                }
            }
        }
    }


    fun sendReport(position: Int){
        viewModelScope.launch(dispatcher.io) {
            val sendReportResult = neewsRepository.reportNeew(ReportRequest(position))
            sendReportResult.ifSuccessful {
                pShowMessageLiveData.value = "گزارش با موفقیت ارسال شد =)"
            }.otherwise {
                pShowMessageLiveData.value = when(it){
                    is CommonExceptions.ConnectionException -> "اینترنت شما قطع می‌باشد =("
                    else -> "ارسال گزارش با خطا روبرو شد =("
                }
            }
        }
    }

}