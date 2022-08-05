package com.example.royalclips.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.royalclips.R
import com.example.royalclips.databinding.ItemSelectServiceBinding
import com.example.royalclips.model.Constants.BASE_IMAGE_URL
import com.example.royalclips.model.data.getBarberServices.Service
import com.example.royalclips.view.SelectServiceActivity
import com.example.royalclips.viewmodel.SelectServiceViewModel

class SelectServiceAdapter(private val context: Context, private val infoList: List<Service>) :
    RecyclerView.Adapter<SelectServiceAdapter.SelectServiceHolder>() {
    lateinit var viewModel: SelectServiceViewModel
    lateinit var binding: ItemSelectServiceBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectServiceHolder {
        binding = ItemSelectServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(context as SelectServiceActivity)[SelectServiceViewModel::class.java]
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


    inner class SelectServiceHolder(private val binding: ItemSelectServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            binding.tvServiceName.text = service.serviceName
            binding.tvCost.text = service.cost.toString()
            binding.tvDuration.text = service.duration.toString()
            if(service.serviceId in viewModel.barberServicesSelectLiveData.value!!){
                binding.ivSelect.isSelected = true
            }
            binding.root.setOnClickListener {
                binding.ivSelect.isSelected = !binding.ivSelect.isSelected
                if (binding.ivSelect.isSelected){
                    binding.ivSelect.setImageResource(R.drawable.ic_baseline_check_circle_24)
                }else{
                    binding.ivSelect.setImageResource(R.drawable.selector_check_circle_outline_24)
                }

                if (binding.ivSelect.isSelected && !viewModel.barberServicesSelectLiveData.value!!.contains(
                        service.serviceId
                    )
                ) {
                    viewModel.barberServicesSelectLiveData.value!!.add(service.serviceId)

                }

                if (!binding.ivSelect.isSelected && viewModel.barberServicesSelectLiveData.value!!.contains(
                        service.serviceId
                    )
                ) {
                    viewModel.barberServicesSelectLiveData.value!!.remove(service.serviceId)
                }
                Log.e(
                    "barberServicesSelect",
                    viewModel.barberServicesSelectLiveData.value.toString()
                )
            }

            Glide.with(context)
                .load(BASE_IMAGE_URL + service.servicePic)
                .into(binding.ivServicePic)
        }
    }
}
