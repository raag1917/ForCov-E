package com.raag.forcove.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.raag.forcove.data.Report

class Repo {

    fun getData(email: String): LiveData<MutableList<Report>> {
        val mutableData = MutableLiveData<MutableList<Report>>()
        FirebaseFirestore.getInstance().collection(email).get().addOnSuccessListener {
            val listData = mutableListOf<Report>()
            for (document in it) {
                val id = document.getString("id")
                val name = document.getString("name")
                val mail = document.getString("mail")
                val date = document.getString("date")
                val r1 = document.getString("r1")
                val r2 = document.getString("r2")
                val r3 = document.getString("r3")
                val r4 = document.getString("r4")
                val r5 = document.getString("r5")
                val r6 = document.getString("r6")
                val r7 = document.getString("r7")
                val risk = document.getString("risk")

                val report = Report(id!!, name!!, mail!!, date!!, r1!!, r2!!, r3!!, r4!!, r5!!, r6!!, r7!!, risk!!)
                listData.add(report)
            }
            mutableData.value = listData
        }
        return mutableData
    }
}