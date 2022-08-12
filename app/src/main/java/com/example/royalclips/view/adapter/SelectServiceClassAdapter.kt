package com.example.royalclips.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.royalclips.databinding.ItemSelectServiceClassBinding
import com.example.royalclips.view.SelectServiceActivity
import com.example.royalclips.viewmodel.SelectServiceViewModel

class SelectServiceClassAdapter(
    private val context: Context, private val serviceTypeArrayList: ArrayList<String>
) :
    RecyclerView.Adapter<SelectServiceClassAdapter.SelectServiceHolder>() {
    lateinit var adapter: SelectServiceAdapter
    lateinit var viewModel: SelectServiceViewModel
    lateinit var binding: ItemSelectServiceClassBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectServiceHolder {
        binding = ItemSelectServiceClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(context as SelectServiceActivity)[SelectServiceViewModel::class.java]

        return SelectServiceHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectServiceHolder, position: Int) {
        holder.apply {
            val info = serviceTypeArrayList[position]
            holder.bind(info)
        }
    }

    override fun getItemCount(): Int {
        return serviceTypeArrayList.size
    }


    inner class SelectServiceHolder(private val binding: ItemSelectServiceClassBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(serviceType: String) {
            binding.tvServiceClass.text = serviceType
            adapter = SelectServiceAdapter(
                context,
                viewModel.barberServicesLiveData.value!!.filter { it.serviceType == serviceType })
            binding.rvServices.adapter = adapter
            binding.rvServices.layoutManager = LinearLayoutManager(context)
        }
    }
}