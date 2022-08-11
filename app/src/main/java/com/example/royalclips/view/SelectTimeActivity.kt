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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivitySelectTimeBinding
import com.example.royalclips.model.data.bookAppointments.Service
import com.example.royalclips.model.data.getBarber.Barber
import com.example.royalclips.view.AppointmentInfoActivity.Companion.RESCHEDULE_APPOINTMENT_ID
import com.example.royalclips.view.SelectServiceActivity.Companion.SELECTED_SERVICE
import com.example.royalclips.view.SelectServiceActivity.Companion.SLOT_NUMBER
import com.example.royalclips.view.adapter.SelectBarberAdapter.Companion.BARBER
import com.example.royalclips.view.adapter.SelectDateAdapter
import com.example.royalclips.view.adapter.SelectTimeAdapter
import com.example.royalclips.viewmodel.SelectTimeViewModel

class SelectTimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectTimeBinding
    lateinit var viewModel: SelectTimeViewModel
    lateinit var dateAdapter: SelectDateAdapter
    lateinit var timeAdapter: SelectTimeAdapter
    private lateinit var barber: Barber
    private var slotNumber: Int = 0
    private lateinit var selectedService: ArrayList<Service>
    private var isFromReschedule:Boolean = false
    private var rescheduleAppointmentId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SelectTimeViewModel::class.java]
        barber = intent.extras?.get(BARBER) as Barber
        slotNumber = intent.extras?.get(SLOT_NUMBER) as Int
        isFromReschedule = intent.extras?.get(AppointmentInfoActivity.IS_FROM_RESCHEDULE) as Boolean
        selectedService = intent.getParcelableArrayListExtra<Service>(SELECTED_SERVICE) as ArrayList<Service> /* = java.util.ArrayList<kotlin.Int> */

        if(isFromReschedule){
            rescheduleAppointmentId = intent.extras?.get(RESCHEDULE_APPOINTMENT_ID) as Int
        }


        viewModel.setSlotNumber(slotNumber)
        viewModel.loadCurrentAppointments(barber.barberId)
        initView()
        setUpObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

        setSupportActionBar(binding.selectTimeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        binding.tvSelectSlots.text = "Select $slotNumber Slots"

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
            if (viewModel.startFromSlotLiveData.value == -1) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("Time Error")
                    .setMessage("Please select time.")
                    .setPositiveButton("Ok") { _, _ ->
                    }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            } else {
                val intent = Intent(
                    this@SelectTimeActivity,
                    AppointmentSummaryActivity::class.java
                )
                intent.putExtra(BARBER, barber)
                intent.putParcelableArrayListExtra(SELECTED_SERVICE, selectedService)
                intent.putExtra(SELECTED_DATE, viewModel.appointmentsDateLiveData.value)
                intent.putExtra(FROM_TIME,viewModel.getFromAndToTimeString()[0])
                intent.putExtra(TO_TIME, viewModel.getFromAndToTimeString()[1])
                intent.putExtra(AppointmentInfoActivity.IS_FROM_RESCHEDULE,isFromReschedule)
                intent.putExtra(RESCHEDULE_APPOINTMENT_ID,rescheduleAppointmentId)
                startActivity(intent)
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
            dateAdapter = SelectDateAdapter(this, availableSlots, viewModel)
            binding.rvDates.adapter = dateAdapter
            binding.rvDates.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.tvSelectedDayDate.text = "${availableSlots[0].day}, ${availableSlots[0].date}"

        }

        viewModel.appointmentsDateLiveData.observe(this) { date ->
            viewModel.currentAppointmentsLiveData.value!!.forEach() {
                if (it.date == date) {
                    binding.tvSelectedDayDate.text = "${it.day}, ${it.date}"
                    timeAdapter = SelectTimeAdapter(this, it.slots, viewModel, slotNumber)
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

    companion object{
        const val SELECTED_DATE = "SELECTED_DATE"
        const val FROM_TIME = "FROM_TIME"
        const val TO_TIME = "TO_TIME"
    }
}