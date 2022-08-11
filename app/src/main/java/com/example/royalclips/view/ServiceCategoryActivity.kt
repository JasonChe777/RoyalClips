package com.example.royalclips.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityServiceCategoryBinding
import com.example.royalclips.view.adapter.ServiceCategoryAdapter
import com.example.royalclips.viewmodel.ServiceViewModel

class ServiceCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceCategoryBinding
    private lateinit var viewModel: ServiceViewModel
    private lateinit var adapter: ServiceCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceCategoryBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[ServiceViewModel::class.java]
        viewModel.getServiceCategories()
        setContentView(binding.root)

        setSupportActionBar(binding.servicesToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        viewModel.loadingLiveData.observe(this){
            binding.progressBar.isVisible = it
        }

        viewModel.serviceCategoriesLiveData.observe(this){
            adapter = ServiceCategoryAdapter(this, it)
            binding.rvClass.adapter = adapter
            binding.rvClass.layoutManager = GridLayoutManager(this, 2)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}