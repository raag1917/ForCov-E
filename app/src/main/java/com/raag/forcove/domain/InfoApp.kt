package com.raag.forcove.domain

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.raag.forcove.R

class InfoApp {
    @SuppressLint("InflateParams")
    fun info(context: Context){
        val information = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.about, null)
        val version = view.findViewById<TextView>(R.id.versionActual)
        val close = view.findViewById<TextView>(R.id.cerrarAbout)
        information.setContentView(view)
        information.create()
        information.show()
        information.setCanceledOnTouchOutside(false)
        close.setOnClickListener {
            information.dismiss()
        }
        version.text = context.getString(R.string.version_actual)
    }
}