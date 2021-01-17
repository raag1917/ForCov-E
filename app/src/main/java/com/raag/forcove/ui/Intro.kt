package com.raag.forcove.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.raag.forcove.databinding.ActivityIntroBinding

class Intro : AppCompatActivity() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var introList = listOf("Lava tus manos cada 2 horas",
            "Usa el tapabocas siempre que sea necesario",
            "Usa correctamente el tapabocas",
            "Conserva el distanciamiento físico",
            "Respeta el distanciamiento de 2mt",
            "Cumple el protocolo de bioseguridad",
            "Reporta diariamente tus síntomas",
            "Evita visitar las sedes cuando tengas gripa",
            "Reporta cualquier síntoma que presentes relacionados con COVID-19",
            "Prevenir el COVID-19 está en nuestras manos",
            "Ten presentes las recomendaciones de bioseguridad",
            "Cumple siempre los protocolos de bioseguridad"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityIntroBinding.inflate(layoutInflater).apply{
            setContentView(root)
            introMessage.text = introList[(introList.indices).random()]

            val user = mAuth.currentUser

            Handler(Looper.getMainLooper()).postDelayed({
                if(user != null ){
                    val intent = Intent(this@Intro, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@Intro, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 2000)
        }
    }
}