package com.raag.forcove.domain

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.raag.forcove.R
import de.hdodenhof.circleimageview.CircleImageView

class UserData {
    @SuppressLint("InflateParams")
    fun userData(context: Context) {
        val mAuth = FirebaseAuth.getInstance()
        val sheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.info_user, null)
        sheetDialog.setContentView(view)
        val photo = view.findViewById<CircleImageView>(R.id.imgUserId)
        val email = view.findViewById<TextView>(R.id.mailRegistro)
        val userName = view.findViewById<TextView>(R.id.tvUser)
        val idUser = view.findViewById<TextView>(R.id.idRegistro)
        val other = view.findViewById<TextView>(R.id.otherData)
        val closeInfo = view.findViewById<TextView>(R.id.closeInfoUser)
        sheetDialog.create()
        sheetDialog.show()
        sheetDialog.setCanceledOnTouchOutside(false)

        if (mAuth.currentUser!!.photoUrl != null) {
            Glide.with(context).load(mAuth.currentUser!!.photoUrl).into(photo)
        } else {
            photo.setImageResource(R.drawable.logo_forcov_e)
        }
        email.text = mAuth.currentUser!!.email
        userName.text = mAuth.currentUser!!.displayName
        idUser.text = mAuth.currentUser!!.uid
        other.text = mAuth.currentUser!!.providerId

        closeInfo.setOnClickListener {
            sheetDialog.dismiss()
        }

    }
}