package com.example.royalclips.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivitySelectServiceBinding
import com.example.royalclips.model.data.getBarber.Barber
import com.example.royalclips.view.AppointmentInfoActivity.Companion.IS_FROM_RESCHEDULE
import com.example.royalclips.view.adapter.SelectBarberAdapter.Companion.BARBER
import com.example.royalclips.view.adapter.SelectServiceClassAdapter
import com.example.royalclips.viewmodel.SelectServiceViewModel

class SelectServiceActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySelectServiceBinding
    private lateinit var viewModel:SelectServiceViewModel
    private lateinit var adapter: SelectServiceClassAdapter
    private lateinit var barber:Barber
    private  var isFromReschedule:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        barber = intent.extras?.get(BARBER) as Barber
        viewModel = ViewModelProvider(this)[SelectServiceViewModel::class.java]
        viewModel.getServicesByBarber(barber.barberId)
        initView()
        setUpObserver()
    }

    private fun initView() {
        setSupportActionBar(binding.selectServiceToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        binding.btnChangeBarber.setOnClickListener {
            finish()
        }

        binding.btnContinue.setOnClickListener {
            if(viewModel.barberServicesSelectLiveData.value!!.size == 0){
                val builder = AlertDialog.Builder(this)
                    .setTitle("Services Error")
                    .setMessage("Please select at least 1 services.")
                    .setPositiveButton("Ok") { _, _ ->
                    }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            } else {
                val intent = Intent(this@SelectServiceActivity, SelectTimeActivity::class.java)
                intent.putExtra(BARBER, barber)
                intent.putExtra(SLOT_NUMBER,viewModel.getAppointmentsSlot())
                intent.putParcelableArrayListExtra(SELECTED_SERVICE, viewModel.getSelectedServiceList())
                intent.putExtra(IS_FROM_RESCHEDULE,isFromReschedule)
                startActivity(intent)
            }
        }
    }

    private fun setUpObserver() {

        viewModel.loadingLiveData.observe(this){
            binding.progressBar.isVisible = it
        }
        viewModel.barberServicesTypeLiveData.observe(this) {
            it?.let {
                adapter = SelectServiceClassAdapter(this, it)
                binding.rvServices.adapter = adapter
                binding.rvServices.layoutManager = LinearLayoutManager(this)
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
        const val SLOT_NUMBER = "SLOT_NUMBER"
        const val SELECTED_SERVICE = "SELECTED_SERVICE"

    }

}