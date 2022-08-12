package com.example.royalclips.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityWorkingHourBinding
import com.example.royalclips.view.adapter.WorkingHourAdapter
import com.example.royalclips.viewmodel.MainViewModel

class WorkingHourActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkingHourBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: WorkingHourAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkingHourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.WorkingHourToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getWorkingHour()
        viewModel.workingHourLiveData.observe(this) {
            adapter = WorkingHourAdapter(this, it)
            binding.rvServices.adapter = adapter
            binding.rvServices.layoutManager = LinearLayoutManager(this)
        }

        viewModel.getDashboard()
        viewModel.dashboardLiveData.observe(this) {
            binding.tvStatus.text = viewModel.dashboardLiveData.value!!.isShopOpened
            binding.tvStatus.resources.getColor(R.color.white)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}