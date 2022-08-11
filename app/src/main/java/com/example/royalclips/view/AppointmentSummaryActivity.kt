package com.example.royalclips.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityAppointmentSummaryBinding
import com.example.royalclips.model.Constants
import com.example.royalclips.model.data.bookAppointments.Service
import com.example.royalclips.model.data.getBarber.Barber

import com.example.royalclips.view.LoginActivity.Companion.API_TOKEN
import com.example.royalclips.view.LoginActivity.Companion.FILE_NAME
import com.example.royalclips.view.LoginActivity.Companion.USER_ID
import com.example.royalclips.view.SelectTimeActivity.Companion.FROM_TIME
import com.example.royalclips.view.SelectTimeActivity.Companion.SELECTED_DATE
import com.example.royalclips.view.SelectTimeActivity.Companion.TO_TIME
import com.example.royalclips.view.adapter.AppointmentSummaryAdapter
import com.example.royalclips.view.adapter.SelectBarberAdapter
import com.example.royalclips.viewmodel.AppointmentViewModel

class AppointmentSummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentSummaryBinding
    private lateinit var viewModel: AppointmentViewModel
    private lateinit var adapter: AppointmentSummaryAdapter
    private lateinit var barber: Barber
    private lateinit var selectedService: ArrayList<Service>
    private lateinit var date: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var fromTime: String
    private lateinit var toTime: String
    private var userId: Int = 0
    private lateinit var apiToken: String
    private var isFromReschedule: Boolean = false
    private var rescheduleAppointmentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(APPOINTMENT_INFO, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        viewModel = ViewModelProvider(this)[AppointmentViewModel::class.java]
        barber = intent.extras?.get(SelectBarberAdapter.BARBER) as Barber
        isFromReschedule = intent.extras?.get(AppointmentInfoActivity.IS_FROM_RESCHEDULE) as Boolean
        if (isFromReschedule) {
            rescheduleAppointmentId =
                intent.extras?.get(AppointmentInfoActivity.RESCHEDULE_APPOINTMENT_ID) as Int
        }

        userId = getSharedPreferences(FILE_NAME, MODE_PRIVATE).getInt(USER_ID, 0)
        apiToken = getSharedPreferences(FILE_NAME, MODE_PRIVATE).getString(API_TOKEN, "") ?: ""

        selectedService =
            intent.getParcelableArrayListExtra<Service>(SelectServiceActivity.SELECTED_SERVICE) as ArrayList<Service>
        date = intent.extras?.get(SELECTED_DATE) as String
        fromTime = intent.extras?.get(FROM_TIME) as String
        toTime = intent.extras?.get(TO_TIME) as String


        initView()
        setUpObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        setSupportActionBar(binding.bookAppointmentSummaryToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        var duration = 0.0
        selectedService.forEach() {
            duration += it.duration
        }

        val selectedTime = "$fromTime--$toTime (${duration.toInt()} Minutes)"

        var cost = 0.0
        selectedService.forEach() {
            cost += it.cost
        }

        binding.tvSelectedDayDate.text = date
        binding.tvSelectedTime.text = selectedTime
        binding.tvSelectedBarber.text = barber.barberName
        Glide.with(this)
            .load(Constants.BASE_IMAGE_URL + barber.profilePic)
            .into(binding.ivBarberPic)
        binding.rvSelectedService.layoutManager = LinearLayoutManager(this)
        binding.tvTotalCost.text = "Total Cost: ${cost.toInt()} USD"
        adapter = AppointmentSummaryAdapter(this, selectedService)
        binding.rvSelectedService.adapter = adapter
        binding.btnConfirm.text = if (isFromReschedule) "DONE" else "CONFIRM"
        binding.btnConfirm.setOnClickListener {
            val map = HashMap<String, Any>()
            map["userId"] = userId
            map["barberId"] = barber.barberId
            map["services"] = selectedService.map { it.serviceId }
            map["aptDate"] = date
            map["timeFrom"] = fromTime
            map["timeTo"] = toTime
            map["totalDuration"] = duration
            map["totalCost"] = cost
            map["couponCode"] = ""
            map["sendSms"] = false

            val rescheduleMap = HashMap<String, Any>()
            rescheduleMap["aptNo"] = rescheduleAppointmentId
            rescheduleMap["timeFrom"] = fromTime
            rescheduleMap["timeTo"] = toTime
            rescheduleMap["aptDate"] = date

            if (isFromReschedule) {
                viewModel.rescheduleAppointment(apiToken, rescheduleMap)
            } else {
                viewModel.bookAppointment(apiToken, map)
            }

        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun setUpObserver() {
        viewModel.appointmentLiveData.observe(this) {
            if (it != null) {
                val intent = Intent(
                    this@AppointmentSummaryActivity,
                    AppointmentInfoActivity::class.java
                )
                intent.putExtra(APPOINTMENT_ID, it.aptNo)
                intent.putExtra(APPOINTMENT_DETAIL, it)
                editor.putInt(APPOINTMENT_ID, it.aptNo)
                startActivity(intent)
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val APPOINTMENT_INFO = "APPOINTMENT INFO"
        const val APPOINTMENT_ID = "APPOINTMENT ID"
        const val APPOINTMENT_DETAIL = "APPOINTMENT DETAIL"
    }
}