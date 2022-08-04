package com.example.royalclips.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivitySelectBarberBinding
import com.example.royalclips.view.adapter.SelectBarberAdapter
import com.example.royalclips.viewmodel.SelectBarberViewModel

class SelectBarberActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBarberBinding
    private lateinit var viewModel: SelectBarberViewModel
    private lateinit var adapter: SelectBarberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBarberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SelectBarberViewModel::class.java]

        viewModel.getBarbers()

        initView()
        setUpObserver()
    }

    private fun initView() {
        setSupportActionBar(binding.selectBarberToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
    }

    private fun setUpObserver() {

        viewModel.barbersLiveData.observe(this) {
            it?.let {
                adapter = SelectBarberAdapter(this.applicationContext, it)
                binding.rvBarbers.adapter = adapter
                binding.rvBarbers.layoutManager = LinearLayoutManager(this)

            }
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