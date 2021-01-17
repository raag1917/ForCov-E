package com.raag.forcove.ui


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.raag.forcove.R
import com.raag.forcove.application.*
import com.raag.forcove.databinding.ActivityHomeBinding
import com.raag.forcove.domain.Relative
import io.armcha.elasticview.ElasticView

class HomeActivity : AppCompatActivity() {
    companion object{
        const val KEY_STUDENT = "studentData"
    }

    private lateinit var binding: ActivityHomeBinding
    private var backPressedTime = 0L
    private var idUser: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menu.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        binding.misDatos.setOnClickListener {
            saveData()
        }

        binding.btnLeerCodigo.setOnClickListener {
            startActivity(Intent(this, ReadQRActivity::class.java))
        }

        binding.btnMisReportes.setOnClickListener {
            val intent = Intent(this, MyReportsActivity::class.java)
            startActivity(intent)
        }

        binding.btnReportes.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra(KEY_STUDENT, idUser)
            startActivity(intent)
        }

        binding.btnInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        binding.btnPqr.setOnClickListener {
            val intent = Intent(this, PqrActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            ActivityCompat.finishAffinity(this)
        } else {
            Snackbar.make(binding.root, getString(R.string.presiona), Snackbar.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun saveData(){
        val data = AlertDialog.Builder(this)
        val viewData = layoutInflater.inflate(R.layout.mis_datos, null)
        data.setView(viewData)
        val close = viewData.findViewById<TextView>(R.id.cerrarDatos)
        val id = viewData.findViewById<EditText>(R.id.id)
        val save = viewData.findViewById<ElasticView>(R.id.btnGuardarView)
        val dialog = data.create()
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)

        close.setOnClickListener {
            dialog.dismiss()
        }

        save.setOnClickListener {
            if(id.text.isNotEmpty()){
                idUser = id.text.toString()
                sendStudent(idUser)
                Relative().okSave(dialog, idUser, this)
            } else {
                id.error = "Falta tu ID"
            }

        }
    }

    private fun sendStudent(id:String){
        val pref: SharedPreferences = getSharedPreferences(KEY_STUDENT, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(KEY_STUDENT, id)
        editor.apply()
    }
}