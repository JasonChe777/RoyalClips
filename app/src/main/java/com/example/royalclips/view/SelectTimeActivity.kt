package com.example.royalclips.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivitySelectTimeBinding
import com.example.royalclips.view.adapter.SelectDateAdapter
import com.example.royalclips.view.adapter.SelectTimeAdapter
import com.example.royalclips.viewmodel.SelectTimeViewModel

class SelectTimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectTimeBinding
    lateinit var viewModel: SelectTimeViewModel
    lateinit var dateAdapter: SelectDateAdapter
    lateinit var timeAdapter: SelectTimeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SelectTimeViewModel::class.java]
        viewModel.loadCurrentAppointments()
        binding.rvDates.visibility = View.GONE
        viewModel.appointmentsStartFromLiveData.postValue(-1)

        initView()
        setUpObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

        setSupportActionBar(binding.selectTimeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        binding.tvSelectSlots.text = "Select ${viewModel.appointmentsSlotLiveData.value} Slots"

        binding.ivDropDown.isSelected = false
        binding.ivDropDown.setOnClickListener {
            binding.ivDropDown.isSelected = !binding.ivDropDown.isSelected
            if (binding.ivDropDown.isSelected) {
                binding.rvDates.visibility = View.VISIBLE
                binding.ivDropDown.setImageResource(R.drawable.ic_up_arrow)
            } else {
                binding.rvDates.visibility = View.GONE
                binding.ivDropDown.setImageResource(R.drawable.ic_down_arrow)
            }
        }

        binding.btnContinue.setOnClickListener {
            if (viewModel.appointmentsStartFromLiveData.value!! == -1) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("Time Error")
                    .setMessage("Please select time.")
                    .setPositiveButton("Ok") { _, _ ->
                    }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            } else {
                startActivity(
                    Intent(
                        this@SelectTimeActivity,
                        AppointmentSummaryActivity::class.java
                    )
                )
            }

        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpObserver() {

        viewModel.loadingLiveData.observe(this) {
            binding.progressBar.isVisible = it
        }

        viewModel.currentAppointmentsLiveData.observe(this) {
            val availableSlots = it.filter { it.slots.isNotEmpty() }
            dateAdapter = SelectDateAdapter(this, availableSlots)
            binding.rvDates.adapter = dateAdapter
            binding.rvDates.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.tvSelectedDayDate.text = "${availableSlots[0].day}, ${availableSlots[0].date}"
            viewModel.appointmentsDateLiveData.postValue(availableSlots[0].date)
        }

        viewModel.appointmentsDateLiveData.observe(this) { date ->
            viewModel.currentAppointmentsLiveData.value!!.forEach() {
                if (it.date == date) {
                    binding.tvSelectedDayDate.text = "${it.day}, ${it.date}"
                    timeAdapter = SelectTimeAdapter(this, it.slots)
                    binding.rvTimeSlots.adapter = timeAdapter
                    binding.rvTimeSlots.layoutManager = GridLayoutManager(this, 4)
                }
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