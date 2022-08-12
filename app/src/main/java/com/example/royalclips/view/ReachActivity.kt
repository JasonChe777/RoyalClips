package com.example.royalclips.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityReachBinding
import com.example.royalclips.view.adapter.ReachAdapter
import com.example.royalclips.viewmodel.MainViewModel

class ReachActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReachBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ReachAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReachBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.howToReachToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getContacts()
        viewModel.contactsLiveData.observe(this){
            adapter = ReachAdapter(this, it)
            binding.rvContacts.layoutManager = LinearLayoutManager(this)
            binding.rvContacts.adapter = adapter

            viewModel.loadingLiveData.observe(this){
                binding.progressBar.isVisible = it
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}