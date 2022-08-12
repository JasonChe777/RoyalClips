package com.example.royalclips.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.royalclips.databinding.ItemServiceCategoryBinding
import com.example.royalclips.model.Constants
import com.example.royalclips.model.data.getServiceCategory.ServiceCategory
import com.example.royalclips.view.ServiceCategoryActivity
import com.example.royalclips.view.ServiceListActivity
import com.example.royalclips.viewmodel.ServiceViewModel

class ServiceCategoryAdapter(private val context: Context, private val infoList: ArrayList<ServiceCategory>) :
    RecyclerView.Adapter<ServiceCategoryAdapter.SelectServiceHolder>() {
    lateinit var viewModel: ServiceViewModel
    lateinit var binding: ItemServiceCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectServiceHolder {
        binding =
            ItemServiceCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel =
            ViewModelProvider(context as ServiceCategoryActivity)[ServiceViewModel::class.java]
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


    inner class SelectServiceHolder(val binding: ItemServiceCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(serviceCategory: ServiceCategory) {
            binding.tvCategory.text = serviceCategory.category


            Glide.with(context)
                .load(Constants.BASE_IMAGE_URL + serviceCategory.categoryImage)
                .into(binding.ivCategoryPhoto)

            binding.root.setOnClickListener {
                viewModel.serviceCategoryIdLiveData.postValue(serviceCategory.categoryId)
                val intent = Intent(binding.root.context, ServiceListActivity::class.java)
                intent.putExtra(CATEGORY_ID, serviceCategory.categoryId)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object{
        const val CATEGORY_ID = "CATEGORY ID"
    }
}