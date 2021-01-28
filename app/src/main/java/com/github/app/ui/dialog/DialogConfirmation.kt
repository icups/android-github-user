package com.github.app.ui.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class DialogConfirmation(context: Context, message: String = "Are you sure?", val actionListener: () -> Unit) : DialogInterface.OnClickListener {

    private val builder = AlertDialog.Builder(context)

    init {
        builder.run {
            setMessage(message)
            setPositiveButton("YES", this@DialogConfirmation)
            setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        actionListener()
        dialog?.dismiss()
    }

}