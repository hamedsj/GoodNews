package me.pitok.design

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomsheet.BottomSheetDialog

class AlertBottomSheetDialog(
    context: Context
): BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme){

    private lateinit var titleTv: AppCompatTextView
    private lateinit var okBt: AppCompatTextView
    private lateinit var cancelBt: AppCompatTextView

    var title: String = ""
        set(value) {
            field = value
            titleTv.text = value
        }
    var onOkClickListener: () -> Unit = {}
        set(value) {
            field = value
            okBt.setOnClickListener {
                value.invoke()
            }
        }
    var onCancelClickListener: () -> Unit = {}
        set(value) {
            field = value
            cancelBt.setOnClickListener {
                value.invoke()
            }
        }
    var okBtText = ""
        set(value) {
            field = value
            okBt.text = value
        }

    var cancelBtText = ""
        set(value) {
            field = value
            cancelBt.text = value
        }
    var onDismissListener: () -> Unit = {}


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.sheet_alert_dialog,null,false)
        titleTv = view.findViewById(R.id.alertDialogTitle)
        okBt = view.findViewById(R.id.alertDialogOkBt)
        cancelBt = view.findViewById(R.id.alertDialogCancelBt)
        setContentView(view)
        titleTv.text = title
        setListeners()
    }

    private fun setListeners() {
        okBt.setOnClickListener{
            onOkClickListener.invoke()
        }
        cancelBt.setOnClickListener{
            onCancelClickListener.invoke()
        }
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
        onDismissListener.invoke()
    }
}