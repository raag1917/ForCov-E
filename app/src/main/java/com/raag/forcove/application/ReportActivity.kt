package com.raag.forcove.application

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.raag.forcove.R
import com.raag.forcove.databinding.ActivityReportBinding
import com.raag.forcove.google.SendReport
import com.raag.forcove.ui.HomeActivity
import com.raag.forcove.ui.HomeActivity.Companion.KEY_STUDENT
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    companion object{
        const val PERMISSIONS_REQUEST = 10
    }
    private var riesgo: Long = 0
    private var rs1: Long = 0
    private var rs2: Long = 0
    private var rs3: Long = 0
    private var rs4: Long = 0
    private var rs5: Long = 0
    private var rs6: Long = 0
    private var rs7: Long = 0
    private var nameFull = ""
    private lateinit var id: String
    private val date = System.currentTimeMillis()

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("MMMM dd.yyyy h:mm:ss a")
    private val dateString = sdf.format(date).toString()
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(this, permissions)) {
                generateQRCode()
            } else {
                requestPermissions(permissions, PERMISSIONS_REQUEST)
            }
        } else {
            generateQRCode()
        }

        val userName = mAuth.currentUser?.displayName.toString()
        val userMail = mAuth.currentUser?.email.toString()

        val pref: SharedPreferences = getSharedPreferences(HomeActivity.KEY_STUDENT, Context.MODE_PRIVATE)
        id = pref.getString(KEY_STUDENT, "").toString()

        binding.tvMain.text =
                getString(R.string.mensaje) + userName + getString(R.string.correo_de) + userMail + getString(R.string.inscrito) + id

        binding.respuesta1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rs1 = 1
            }
        }
        binding.respuesta2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rs2 = 1
            }
        }
        binding.respuesta3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rs3 = 1
            }
        }
        binding.respuesta4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rs4 = 1
            }
        }
        binding.respuesta5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rs5 = 1
            }
        }
        binding.respuesta6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rs6 = 1
            }
        }
        binding.respuesta7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rs7 = 1
            }
        }

        binding.respuestas.setOnCheckedChangeListener { compoundButton, isSelected ->
            if (isSelected) {
                binding.btnEnviarView.visibility = View.VISIBLE
                compoundButton.text = getString(R.string.ok)
                binding.respuesta1.isClickable = false
                binding.respuesta2.isClickable = false
                binding.respuesta3.isClickable = false
                binding.respuesta4.isClickable = false
                binding.respuesta5.isClickable = false
                binding.respuesta6.isClickable = false
                binding.respuesta7.isClickable = false
            } else {
                compoundButton.text = getString(R.string.completar)
                binding.btnEnviarView.visibility = View.GONE
                binding.respuesta1.isClickable = true
                binding.respuesta2.isClickable = true
                binding.respuesta3.isClickable = true
                binding.respuesta4.isClickable = true
                binding.respuesta5.isClickable = true
                binding.respuesta6.isClickable = true
                binding.respuesta7.isClickable = true
            }
        }

        binding.btnEnviarView.setOnClickListener {
            riesgo = rs1 + rs2 + rs3 + rs4 + rs5 + rs6 + rs7
            val finalRisk = riesgo.toString()
            val rs1S = rs1.toString()
            val rs2S = rs2.toString()
            val rs3S = rs3.toString()
            val rs4S = rs4.toString()
            val rs5S = rs5.toString()
            val rs6S = rs6.toString()
            val rs7S = rs7.toString()
            nameFull = """El código QR contiene la siguiente información: 
                |$dateString - $userName - Riesgo: $finalRisk de 7""".trimMargin()
            SendReport().send(id, this, userName, userMail, dateString, rs1S, rs2S, rs3S, rs4S, rs5S, rs6S, rs7S, finalRisk)
            clear()



        }
    }

    private fun clear() {
        binding.respuestas.isChecked = false
        rs1 = 0
        binding.respuesta1.isChecked = false
        rs2 = 0
        binding.respuesta2.isChecked = false
        rs3 = 0
        binding.respuesta3.isChecked = false
        rs4 = 0
        binding.respuesta4.isChecked = false
        rs5 = 0
        binding.respuesta5.isChecked = false
        rs6 = 0
        binding.respuesta6.isChecked = false
        rs7 = 0
        binding.respuesta7.isChecked = false
    }

    @SuppressLint("SimpleDateFormat")
    private fun generateQRCode() {
        val bitmap = encodeAsBitmap(nameFull, 300, 300, this)
        createImageFile(bitmap, nameFull)
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(bitmapScaled: Bitmap?, name: String) {

        val bytes = ByteArrayOutputStream()
        bitmapScaled?.compress(Bitmap.CompressFormat.PNG, 40, bytes)
        val filepath =
                Environment.getExternalStorageDirectory().absolutePath + File.separator + "$name.png"
        val f = File(filepath)
        f.createNewFile()
        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        fo.close()
    }

    private fun encodeAsBitmap(str: String, WIDTH: Int, HEIGHT: Int, ctx: Context): Bitmap? {
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null)
        } catch (iae: IllegalArgumentException) {
            return null
        }
        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result.get(x, y)) -0x1000000 else -0x1
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    private fun checkPermission(context: Context, permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this,"Abre las configuraciones y activa los permisos en tu celular", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess)
                generateQRCode()
        }
    }

}

