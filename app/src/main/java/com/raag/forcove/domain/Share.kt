package com.raag.forcove.domain

import android.content.Context
import android.content.Intent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.raag.forcove.R

class Share {
    fun share(context: Context) {
        val database = FirebaseDatabase.getInstance()
        val myRefTwo = database.getReference("aa-update")
        myRefTwo.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val it = dataSnapshot.getValue(String::class.java)
                val intent = Intent()
                intent.type = "text/plain"
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    context.getString(R.string.comparte_forcov_e) +
                            "$it"
                )
                intent.putExtra(Intent.EXTRA_SUBJECT, "ForCov E")
                intent.action = Intent.ACTION_SEND
                val chooseIntent = Intent.createChooser(intent, context.getString(R.string.comparte_forcov_e))
                context.startActivity(chooseIntent)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}