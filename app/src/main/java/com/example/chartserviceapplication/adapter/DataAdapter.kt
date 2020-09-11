package com.example.chartserviceapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chartserviceapplication.R
import com.example.chartserviceapplication.databinding.DataItemLayoutBinding
import com.example.chartserviceapplication.interfaces.ItemClickListener
import com.example.chartserviceapplication.model.Data
import com.example.chartserviceapplication.model.FeedData
import com.example.chartserviceapplication.viewmodel.DataViewModel

class DataAdapter(listener: ItemClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    lateinit var dataList: List<Data>
    lateinit var feedDataList: List<FeedData>
    private var listener: ItemClickListener

    init {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DataItemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.data_item_layout,
                parent,
                false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (::dataList.isInitialized) dataList.size else 0
    }

    fun updateList(list: List<Data>, feedList: List<FeedData>) {
        this.dataList = list
        this.feedDataList = feedList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position],feedDataList[position])
        holder.itemView.setOnClickListener {
            listener.onItemlickListener(position, dataList.get(position), feedDataList.get(position))
        }
    }

    class ViewHolder(private val binding: DataItemLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {
        val viewModel = DataViewModel()

        fun bind(product: Data, feedData: FeedData) {
            viewModel.bind(product, feedData)
            binding.viewModel = viewModel
        }
    }
}