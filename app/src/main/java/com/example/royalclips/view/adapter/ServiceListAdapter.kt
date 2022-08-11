package com.example.royalclips.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.royalclips.databinding.ItemBookServiceBinding
import com.example.royalclips.model.Constants.BASE_IMAGE_URL
import com.example.royalclips.model.data.getServicesByCategory.ServiceDetail
import com.example.royalclips.view.ServiceDetailsActivity
import com.example.royalclips.view.ServiceListActivity
import com.example.royalclips.viewmodel.ServiceViewModel


class ServiceListAdapter(private val context: Context, private val infoList: List<ServiceDetail>) :
    RecyclerView.Adapter<ServiceListAdapter.SelectServiceHolder>() {
    lateinit var viewModel: ServiceViewModel
    lateinit var binding: ItemBookServiceBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectServiceHolder {
        binding = ItemBookServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(context as ServiceListActivity)[ServiceViewModel::class.java]
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
        fun bind(service: ServiceDetail) {
            binding.tvServiceName.text = service.serviceName
            binding.tvCost.text = service.cost.toInt().toString()+ " USD"
            binding.tvDuration.text = service.duration.toInt().toString()+ " Minutes"
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, ServiceDetailsActivity::class.java)
                intent.putExtra(SERVICE_DETAIL,service)
                binding.root.context.startActivity(intent)
            }
            Glide.with(context)
                .load(BASE_IMAGE_URL + service.servicePic)
                .into(binding.ivServicePic)
        }
    }

    companion object{
        const val SERVICE_DETAIL = "SERVICE DETAIL"
    }
}
