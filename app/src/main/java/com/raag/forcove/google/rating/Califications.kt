package com.raag.forcove.google.rating

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.raag.forcove.R
import com.raag.forcove.google.rating.funcion.Rating
import io.armcha.elasticview.ElasticView

class Califications {

    fun calificaiones(context: Context, view: View) {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val calificaciones = MaterialAlertDialogBuilder(context)
        val viewDialog = LayoutInflater.from(context).inflate(R.layout.calificanos, null)
        calificaciones.setView(viewDialog)
        val estrellas = viewDialog.findViewById<RatingBar>(R.id.ratingBar)
        val calificacion = viewDialog.findViewById<ElasticView>(R.id.bt_calificanos)
        val cierre = viewDialog.findViewById<TextView>(R.id.cerrar_calificacion)
        val dialogs = calificaciones.create()
        dialogs.show()

        cierre.setOnClickListener {
            dialogs.dismiss()
        }

        estrellas.setOnRatingBarChangeListener { ratingBar, _, _ ->
            if (ratingBar.isPressed) {
                calificacion.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                calificacion.setOnClickListener {
                    dialogs.dismiss()
                    val dat = estrellas.rating.toString()
                    Rating().ratings(mAuth.currentUser!!.email.toString(), dat, context, view)
                }
            }
        }
    }
}