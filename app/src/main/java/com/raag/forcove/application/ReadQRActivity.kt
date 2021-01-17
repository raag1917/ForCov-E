package com.raag.forcove.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.raag.forcove.R
import com.raag.forcove.databinding.ActivityReadQRBinding

class ReadQRActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?){
        when(view?.id){
            R.id.btnReadCode -> {
                intentIntegrator.setBeepEnabled(true).initiateScan()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(intentResult != null){
            if(intentResult.contents != null){
                val code = findViewById<TextView>(R.id.tvReadCode)
                code.text = intentResult.contents.toString()
            } else {
                Toast.makeText(this, "Error al leer el código QR", Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private lateinit var intentIntegrator: IntentIntegrator
    lateinit var binding: ActivityReadQRBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadQRBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentIntegrator = IntentIntegrator(this)
        binding.btnReadCode.setOnClickListener(this)
    }
}