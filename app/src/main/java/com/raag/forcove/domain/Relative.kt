package com.raag.forcove.domain


import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.raag.forcove.R

class Relative {
    fun okSave(it: androidx.appcompat.app.AlertDialog, id: String, context: Context) {
        it.dismiss()
        MaterialAlertDialogBuilder(context)
                .setTitle(R.string.app_name)
                .setMessage("Se ha guardado el Número de documento: $id")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }


}