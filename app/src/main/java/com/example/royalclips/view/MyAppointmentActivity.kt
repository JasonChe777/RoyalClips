package com.example.royalclips.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityMyAppointmentBinding
import com.example.royalclips.view.adapter.MyAppointmentAdapter
import com.example.royalclips.viewmodel.AppointmentViewModel

class MyAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyAppointmentBinding
    private lateinit var adapter: MyAppointmentAdapter
    private lateinit var viewModel: AppointmentViewModel
    private var userId: Int = 0
    private lateinit var apiToken: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = getSharedPreferences(LoginActivity.FILE_NAME, MODE_PRIVATE).getInt(LoginActivity.USER_ID, 0)
        apiToken = getSharedPreferences(LoginActivity.FILE_NAME, MODE_PRIVATE).getString(
            LoginActivity.API_TOKEN, "") ?: ""

        setSupportActionBar(binding.myAppointmentToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        viewModel = ViewModelProvider(this)[AppointmentViewModel::class.java]
        viewModel.getAppointments(userId, apiToken)

        viewModel.appointmentInfoLiveData.observe(this){
            adapter = MyAppointmentAdapter(this, it)
            binding.rvAppointments.adapter = adapter
            binding.rvAppointments.layoutManager = LinearLayoutManager(this)
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