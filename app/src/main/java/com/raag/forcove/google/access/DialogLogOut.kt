package com.raag.forcove.google.access

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.raag.forcove.R
import com.raag.forcove.google.access.funcion.LogOut

class DialogLogOut {
    fun close(context: Context) {
        val dialogClose = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialogos, null)
        dialogClose.setView(view)
        val title = view.findViewById<TextView>(R.id.title)
        val message = view.findViewById<TextView>(R.id.message)
        val cancel = view.findViewById<Button>(R.id.bt_uno)
        val ok = view.findViewById<Button>(R.id.bt_dos)

        val alertDialog: AlertDialog = dialogClose.create()
        alertDialog.show()

        title.text = context.getString(R.string.app_name)
        cancel.text = context.getString(R.string.cancelar)
        ok.text = context.getString(R.string.cerrar_sesi_n)
        message.text = context.getString(R.string.cerrar_dialogo)

        cancel.setOnClickListener {
            alertDialog.dismiss()
        }
        ok.setOnClickListener {
            LogOut().logout(context)
        }
    }
}