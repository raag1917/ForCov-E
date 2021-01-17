package com.raag.forcove.application

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.raag.forcove.databinding.ActivityMenuBinding
import com.raag.forcove.domain.InfoApp
import com.raag.forcove.domain.Share
import com.raag.forcove.domain.UserData
import com.raag.forcove.domain.comunication.ContactUs
import com.raag.forcove.google.access.DialogLogOut
import com.raag.forcove.google.rating.Califications

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var mAuth: FirebaseAuth


    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        val imgAccount = mAuth.currentUser?.photoUrl
        if(imgAccount!=null){
            Glide.with(this).load(imgAccount).into(binding.imgUser)
        }

        binding.imgUser.setOnClickListener {
            UserData().userData(this)
        }

        binding.tvUser.text = mAuth.currentUser?.displayName
        binding.mailRegistro.text = mAuth.currentUser?.email

        binding.share.setOnClickListener {
            Share().share(this)
        }

        binding.escribenos.setOnClickListener {
            ContactUs().contacts(this, binding.root)
        }

        binding.support.setOnClickListener {
            chat()
        }

        binding.calificar.setOnClickListener {
            Califications().calificaiones(this, binding.root)
        }

        binding.logout2.setOnClickListener {
            DialogLogOut().close(this)
        }

        binding.close2.setOnClickListener {
            ActivityCompat.finishAffinity(this)
        }

        binding.about2.setOnClickListener {
            InfoApp().info(this)
        }

    }


    private fun chat(){
        val url = "https://api.whatsapp.com/send?phone=+57 3044782641"
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } catch (e: PackageManager.NameNotFoundException) {
            Snackbar.make(binding.root, "Whatsapp no está instalada en tu celular", Snackbar.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}