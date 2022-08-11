package com.example.royalclips.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.royalclips.databinding.ItemBookServiceBinding
import com.example.royalclips.model.Constants.BASE_IMAGE_URL
import com.example.royalclips.model.data.bookAppointments.Service

import com.example.royalclips.view.AppointmentInfoActivity
import com.example.royalclips.viewmodel.AppointmentViewModel

class AppointmentInfoAdapter(private val context: Context, private val infoList: List<Service>) :
    RecyclerView.Adapter<AppointmentInfoAdapter.SelectServiceHolder>() {
    lateinit var viewModel: AppointmentViewModel
    lateinit var binding: ItemBookServiceBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectServiceHolder {
        binding = ItemBookServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(context as AppointmentInfoActivity)[AppointmentViewModel::class.java]
        return SelectServiceHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectServiceHolder, position: Int) {
        holder.apply {
            val info = infoList[position]
            holder.bind(info)
        }
    }

    override fun getItemCount(): Int {
        return infoList.size
    }


    inner class SelectServiceHolder(val binding: ItemBookServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(service: Service) {
            binding.tvServiceName.text = service.serviceName
            binding.tvCost.text = service.cost.toInt().toString()+" USD"
            binding.tvDuration.text = service.duration.toInt().toString()+" Minutes"


            Glide.with(context)
                .load(BASE_IMAGE_URL + service.servicePic)
                .into(binding.ivServicePic)
        }
    }
}
