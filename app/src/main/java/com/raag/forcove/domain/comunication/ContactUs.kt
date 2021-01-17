package com.raag.forcove.domain.comunication

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.raag.forcove.R
import io.armcha.elasticview.ElasticView

class ContactUs {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    fun contacts(context: Context, views: View) {
        val contacts = MaterialAlertDialogBuilder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.contacto, null)
        contacts.setView(view)
        val editTextMessage = view.findViewById<EditText>(R.id.etMensaje)
        val buttonSend = view.findViewById<ElasticView>(R.id.button_enviar)
        val close = view.findViewById<TextView>(R.id.cerrar_contactos)
        val dialog = contacts.create()
        dialog.show()

       close.setOnClickListener {
            dialog.dismiss()
        }

        editTextMessage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonSend.isEnabled = true
                buttonSend.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }
        })

        buttonSend.setOnClickListener {
            if (editTextMessage.text.isEmpty()) {
                editTextMessage.error = context.getString(R.string.falta_mensaje)
            }  else {
                val theName = mAuth.currentUser!!.displayName
                val message = editTextMessage.text.toString()
                val email = mAuth.currentUser!!.email
                editTextMessage.text.clear()
                editTextMessage.requestFocus()
                dialog.dismiss()
                Communication().communication(email, theName!!, message, context, views)
            }

        }
    }
}