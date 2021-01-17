package com.raag.forcove.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.raag.forcove.adapter.MainAdapter
import com.raag.forcove.model.MainViewModel
import com.raag.forcove.databinding.ActivityMyReportsBinding

class MyReportsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyReportsBinding

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var adapter: MainAdapter
    private val viewModel by lazy { ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()
        val mail = mAuth.currentUser?.email!!

        getData(mail)

    }

    private fun getData (mail: String){
        viewModel.fetchData(mail).observeForever{ reports ->
            binding.recyclerView.adapter = adapter
            adapter.setListData(reports)
            adapter.notifyDataSetChanged()
        }
    }
}