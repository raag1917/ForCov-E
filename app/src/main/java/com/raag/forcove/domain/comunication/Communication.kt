package com.raag.forcove.domain.comunication


import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.raag.forcove.R

class Communication {
    fun communication(correo: String?, nombre: String, mensaje: String, context:Context, view: View) {
        val db2 = FirebaseFirestore.getInstance()
        val hashMap = hashMapOf(
            "email" to correo,
            "nombre" to nombre,
            "mensaje" to mensaje
        )
        db2.collection("Mensajes")
            .add(hashMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(view, context.getString(R.string.mensaje_enviado), Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, context.getString(R.string.error_dos), Snackbar.LENGTH_LONG).show()
                }
            }
    }
}