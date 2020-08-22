package me.pitok.neewslist.views

import android.view.View
import com.airbnb.epoxy.EpoxyController
import me.pitok.neew.entity.NeewEntity

class NeewListController(
    private val onReportClick: (Int)->Unit) :
    EpoxyController() {

    var items: MutableList<NeewEntity> = mutableListOf()

    override fun buildModels() {
        items.forEach {item ->
            NeewListModel_()
                .neewEntity(item)
                .onReportClick(View.OnClickListener {
                    onReportClick.invoke(items.indexOf(item))
                })
        }
    }
}