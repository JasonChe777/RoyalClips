package com.example.royalclips.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityAppointmentInfoBinding
import com.example.royalclips.model.Constants
import com.example.royalclips.model.data.bookAppointments.Appointment
import com.example.royalclips.model.data.bookAppointments.Service
import com.example.royalclips.model.data.getBarber.Barber
import com.example.royalclips.view.AppointmentSummaryActivity.Companion.APPOINTMENT_DETAIL
import com.example.royalclips.view.AppointmentSummaryActivity.Companion.APPOINTMENT_ID
import com.example.royalclips.view.SelectServiceActivity.Companion.SELECTED_SERVICE
import com.example.royalclips.view.SelectServiceActivity.Companion.SLOT_NUMBER
import com.example.royalclips.view.adapter.AppointmentInfoAdapter
import com.example.royalclips.view.adapter.SelectBarberAdapter.Companion.BARBER
import com.example.royalclips.viewmodel.AppointmentViewModel
import com.google.android.material.snackbar.Snackbar

class AppointmentInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentInfoBinding
    private lateinit var viewModel: AppointmentViewModel
    private lateinit var adapter: AppointmentInfoAdapter
    private var appointmentId: Int = 0
    lateinit var appointmentDetail: Appointment
    private lateinit var apiToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appointmentId = intent.getIntExtra(APPOINTMENT_ID,0)
        appointmentDetail = intent.extras?.get(APPOINTMENT_DETAIL) as Appointment
        apiToken = getSharedPreferences(
            LoginActivity.FILE_NAME,
            MODE_PRIVATE
        ).getString(LoginActivity.API_TOKEN, "") ?: ""

        viewModel = ViewModelProvider(this)[AppointmentViewModel::class.java]
        viewModel.getAppointmentDetail(apiToken, appointmentId)
        initView()
        setUpObserver()
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {

        setSupportActionBar(binding.bookAppointmentDetailsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)


        binding.btnCancel.setOnClickListener {
            val builder = AlertDialog.Builder(this)
                .setTitle("Confirm Cancel")
                .setMessage("Are your sure you want to cancel this appointment? Once cancelled, you will be no more able to claim for this appointment.")
                .setPositiveButton("Confirm") { _, _ ->
                    viewModel.cancelAppointment(apiToken, appointmentId)
                }
                .setNegativeButton("Cancel") { _, _ -> }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()
        }
        binding.btnReschedule.setOnClickListener {

            val builder = AlertDialog.Builder(this)
                .setTitle("Confirm Reschedule")
                .setMessage("Are your sure you want to reschedule this appointment to a different time?")
                .setPositiveButton("Confirm") { _, _ ->
                    val intent = Intent(this@AppointmentInfoActivity, SelectTimeActivity::class.java)
                    intent.putExtra(
                        BARBER,
                        Barber(
                            barberId = viewModel.appointmentLiveData.value!!.barberId!!,
                            barberName = viewModel.appointmentLiveData.value!!.barberName!!,
                            profilePic = viewModel.appointmentLiveData.value!!.profilePic!!
                        )
                    )
                    intent.putExtra(SLOT_NUMBER,getSlotNumber(viewModel.appointmentLiveData.value!!.totalDuration!!))
                    intent.putParcelableArrayListExtra(SELECTED_SERVICE,getServiceArrayList(viewModel.appointmentLiveData.value!!.services))
                    intent.putExtra(IS_FROM_RESCHEDULE, true)
                    intent.putExtra(RESCHEDULE_APPOINTMENT_ID,viewModel.appointmentLiveData.value!!.aptNo)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel") { _, _ -> }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()


        }

        viewModel.initAppointment(appointmentDetail)
    }

    private fun getSlotNumber(totalDuration: Double):Int{
        return if ((totalDuration % 15).toInt() == 0 ){
            (totalDuration/15).toInt()
        }else{
            (totalDuration/15).toInt() + 1
        }
    }

    private fun getServiceArrayList(list:List<Service>):ArrayList<Service>{
        var arrayList:ArrayList<Service> = arrayListOf()
        list.forEach { arrayList.add(it) }
        return arrayList
    }

    @SuppressLint("SetTextI18n")
    private fun setUpAppointment(appointment: Appointment) {
        binding.tvSelectedDayDate.text = appointment.aptDate
        binding.tvSelectedTime.text =
            "${appointment.timeFrom} to ${appointment.timeTo} (${appointment.totalDuration} Minutes) - ${appointment.aptStatus}"
        binding.tvSelectedBarber.text = appointment.barberName
        Glide.with(this)
            .load(Constants.BASE_IMAGE_URL + appointment.profilePic)
            .into(binding.ivBarberPic)
        adapter = AppointmentInfoAdapter(this, appointment.services)
        binding.rvSelected.adapter = adapter
        binding.rvSelected.layoutManager = LinearLayoutManager(this)
        binding.tvAptNo.text = appointment.aptNo.toString()
        binding.tvTotalCost.text = "Total cost: ${appointment.totalCost?.toInt().toString()} USD"

        if (appointment.aptStatus == "Confirmed") {
            Glide.with(this)
                .load(R.drawable.confirmed)
                .into(binding.ivStamp)
            binding.btnCancel.visibility = View.VISIBLE
            binding.btnReschedule.visibility = View.VISIBLE
        } else if (appointment.aptStatus == "Rescheduled") {
            Glide.with(this)
                .load(R.drawable.rescheduled)
                .into(binding.ivStamp)
            binding.btnCancel.visibility = View.VISIBLE
            binding.btnReschedule.visibility = View.VISIBLE
        } else {
            Glide.with(this)
                .load(R.drawable.canceled)
                .into(binding.ivStamp)
            binding.btnCancel.visibility = View.GONE
            binding.btnReschedule.visibility = View.GONE
        }
    }


    private fun setUpObserver() {
        viewModel.appointmentLiveData.observe(this) { appointment ->
            setUpAppointment(appointment)

        }

        viewModel.loadingLiveData.observe(this){
            binding.progressBar.isVisible = it
        }

        viewModel.cancelSuccess.observe(this) {
            if (it == true) {
                Snackbar.make(binding.root, "Appointment Cancelled", Snackbar.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        startActivity(
                            Intent(
                                this@AppointmentInfoActivity,
                                MainActivity::class.java
                            )
                        )
                    }, 2000
                )

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            startActivity(Intent(this@AppointmentInfoActivity, MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val IS_FROM_RESCHEDULE = "IS FROM RESCHEDULE"
        const val RESCHEDULE_APPOINTMENT_ID ="RESCHEDULE APPOINTMENT ID"
    }
}