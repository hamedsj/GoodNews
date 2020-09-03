package me.pitok.neewslist.views

import android.view.View
import com.airbnb.epoxy.EpoxyController
import me.pitok.neew.entity.NeewEntity

class NeewListController(
    private val onReportClick: (Int)->Unit) :
    EpoxyController() {

    lateinit var items: List<NeewEntity>

    override fun buildModels() {
        val ids = mutableListOf<Int>()
        items.forEach startOfForeach@{ item ->
            if (ids.contains(item._id)) return@startOfForeach
            NeewListModel_()
                .id(item._id)
                .neewEntity(item)
                .onReportClick(View.OnClickListener {
                    onReportClick.invoke(items.indexOf(item))
                })
                .addTo(this)
            ids.add(item._id)
        }
    }
}