package com.example.royalclips.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityServiceListBinding
import com.example.royalclips.view.adapter.ServiceCategoryAdapter.Companion.CATEGORY_ID
import com.example.royalclips.view.adapter.ServiceListAdapter
import com.example.royalclips.viewmodel.ServiceViewModel

class ServiceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceListBinding
    private lateinit var viewModel: ServiceViewModel
    private lateinit var adapter: ServiceListAdapter
    private var categoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityServiceListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        categoryId = intent.extras?.get(CATEGORY_ID) as Int
        setSupportActionBar(binding.serviceListToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        viewModel = ViewModelProvider(this)[ServiceViewModel::class.java]
        viewModel.getServicesByCategory(categoryId)
        viewModel.serviceCategoryIdLiveData.observe(this){
            viewModel.getServicesByCategory(it)
        }


        viewModel.servicesListLiveData.observe(this){
            adapter = ServiceListAdapter(this, it)
            binding.rvServices.adapter = adapter
            binding.rvServices.layoutManager = LinearLayoutManager(this)
        }

        viewModel.loadingLiveData.observe(this){
            binding.progressBar.isVisible = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}