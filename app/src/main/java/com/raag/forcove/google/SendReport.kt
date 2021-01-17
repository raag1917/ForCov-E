package com.raag.forcove.google

import android.content.Context
import android.content.Intent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.raag.forcove.R

class SendReport: Intent() {
    private val db = FirebaseFirestore.getInstance()
    fun send(id: String, context: Context, name: String, mail: String, date: String, r1: String, r2: String, r3: String, r4: String, r5: String, r6: String, r7: String, finalRisk: String) {
        db.collection(mail).document(date).set(
                hashMapOf(
                        "r1" to r1,
                        "r2" to r2,
                        "r3" to r3,
                        "r4" to r4,
                        "r5" to r5,
                        "r6" to r6,
                        "r7" to r7,
                        "risk" to finalRisk,
                        "date" to date,
                        "name" to name,
                        "mail" to mail,
                        "id" to id))

        MaterialAlertDialogBuilder(context)
                .setTitle(context.resources.getString(R.string.app_name))
                .setMessage("Tu reporte se ha enviado con éxito. Nivel de riesgo: $finalRisk de 7")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

    }
}