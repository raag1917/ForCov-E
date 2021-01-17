package com.raag.forcove.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raag.forcove.data.Report
import com.raag.forcove.repository.Repo

class MainViewModel: ViewModel() {
    private val repo = Repo()
    fun fetchData(mail: String): LiveData<MutableList<Report>> {
        val mutableData = MutableLiveData<MutableList<Report>>()
        repo.getData(mail).observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}