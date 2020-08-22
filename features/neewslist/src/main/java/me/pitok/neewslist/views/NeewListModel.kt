package me.pitok.neewslist.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.*
import kotlinx.android.synthetic.main.item_neews_list.view.*
import me.pitok.neew.entity.NeewEntity
import me.pitok.neewslist.R
import me.pitok.sdkextentions.toLeftDate


@EpoxyModelClass
abstract class NeewListModel :
    EpoxyModelWithHolder<NeewListModel.NeewListViewHolder>(){

    @EpoxyAttribute lateinit var neewEntity: NeewEntity
    @EpoxyAttribute lateinit var onReportClick: View.OnClickListener

    inner class NeewListViewHolder : EpoxyHolder(){
        lateinit var content: TextView
        lateinit var date: TextView
        lateinit var reportIc: ImageView

        override fun bindView(itemView: View) {
            content = itemView.itemNeewsListContent
            date = itemView.itemNeewsListDate
            reportIc = itemView.itemNeewsListReportIc
        }
    }

    override fun bind(holder: NeewListViewHolder) {
        super.bind(holder)
        with(neewEntity) {
            holder.content.text = content
            holder.date.text = date.toLeftDate()
            holder.reportIc.setOnClickListener(onReportClick)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.item_neews_list
    }

}