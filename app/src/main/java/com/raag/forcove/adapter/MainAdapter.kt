package com.raag.forcove.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raag.forcove.R
import com.raag.forcove.data.Report
import com.raag.forcove.databinding.ItemReportsBinding

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var dataList = mutableListOf<Report>()

    fun setListData(data: MutableList<Report>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(
        R.layout.item_reports, parent, false))

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemReportsBinding.bind(view)

        fun bind(item: Report) = with(binding) {
            this.tvDate.text = item.date
            this.tvUserName.text = item.name
            this.tvID.text = item.id
            this.tvR1.text = item.r1
            this.tvR2.text = item.r2
            this.tvR3.text = item.r3
            this.tvR4.text = item.r4
            this.tvR5.text = item.r5
            this.tvR6.text = item.r6
            this.tvR7.text = item.r7
            this.tvRisk.text = item.risk
        }
    }
}