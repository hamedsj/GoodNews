package me.pitok.neewslist.views

import android.util.Log
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import me.pitok.neew.entity.NeewEntity

class NeewListController(
    private val onReportClick: (Int)->Unit) :
    TypedEpoxyController<List<NeewEntity>>() {

    override fun buildModels(items: List<NeewEntity>) {
        Log.e("buildModels called", "$items")
        items.forEach {item ->
            NeewListModel_()
                .neewEntity(item)
                .onReportClick(View.OnClickListener {
                    onReportClick.invoke(items.indexOf(item))
                })
        }
    }
}