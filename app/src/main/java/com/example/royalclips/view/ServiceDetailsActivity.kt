package com.example.royalclips.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityServiceDetailsBinding
import com.example.royalclips.model.Constants
import com.example.royalclips.model.data.getServicesByCategory.ServiceDetail
import com.example.royalclips.view.adapter.ServiceListAdapter.Companion.SERVICE_DETAIL
import com.example.royalclips.viewmodel.ServiceViewModel

class ServiceDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceDetailsBinding
    private lateinit var viewModel: ServiceViewModel
    private lateinit var service: ServiceDetail

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        service = intent.getParcelableExtra(SERVICE_DETAIL)!!

        setSupportActionBar(binding.serviceDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        viewModel = ViewModelProvider(this)[ServiceViewModel::class.java]



        service.let {
            binding.tvServiceName.text = "Service name:   ${it.serviceName}"
            binding.tvCost.text = "Cost:   ${service.cost.toInt()} USD"
            binding.tvDuration.text = "Duration:   ${service.duration.toInt()} Minutes"
            binding.tvDescription.text = it.description


            Glide.with(this)
                .load(Constants.BASE_IMAGE_URL + it.servicePic)
                .into(binding.ivServicePic)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}