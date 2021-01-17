package com.raag.forcove.google.access.funcion

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.raag.forcove.ui.AuthActivity

class LogOut {
    fun logout(context: Context) {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        val i = Intent(context, AuthActivity::class.java)
        context.startActivity(i)
    }
}