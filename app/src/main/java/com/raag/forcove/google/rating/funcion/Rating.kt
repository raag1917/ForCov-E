package com.raag.forcove.google.rating.funcion

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.raag.forcove.R

class Rating {
    fun ratings(correo: String?, valoracion: String, context: Context, view:View) {
        val db = FirebaseFirestore.getInstance()
        val hashMap = hashMapOf(
            "valoracion" to valoracion,
            "correo" to correo
        )
        db.collection("Valoraciones")
            .add(hashMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(view, context.getString(R.string.gracias), Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, context.getString(R.string.error), Snackbar.LENGTH_LONG).show()
                }
            }
    }
}