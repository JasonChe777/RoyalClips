package com.example.royalclips.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.royalclips.R
import com.example.royalclips.databinding.ItemMyappointmentBinding
import com.example.royalclips.model.data.getAppointments.AppointmentInfo
import com.example.royalclips.model.data.getAppointments.toAppointment
import com.example.royalclips.view.AppointmentInfoActivity
import com.example.royalclips.view.AppointmentSummaryActivity.Companion.APPOINTMENT_DETAIL
import com.example.royalclips.view.AppointmentSummaryActivity.Companion.APPOINTMENT_ID
import com.example.royalclips.view.MyAppointmentActivity
import com.example.royalclips.viewmodel.AppointmentViewModel

class MyAppointmentAdapter(
    private val context: Context,
    private val infoArrayList: ArrayList<AppointmentInfo>
) :
    RecyclerView.Adapter<MyAppointmentAdapter.AppointmentHolder>() {
    lateinit var viewModel: AppointmentViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        val binding =
            ItemMyappointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel =
            ViewModelProvider(context as MyAppointmentActivity)[AppointmentViewModel::class.java]
        return AppointmentHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        holder.apply {
            val info = infoArrayList[position]
            holder.bind(info)
        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }


    inner class AppointmentHolder(val binding: ItemMyappointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(appointmentInfo: AppointmentInfo) {
            binding.tvAptDate.text = appointmentInfo.aptDate
            binding.tvAptTime.text =
                "${appointmentInfo.timeFrom} to ${appointmentInfo.timeTo} (${appointmentInfo.totalDuration} Minutes)"
            binding.tvStatus.text = appointmentInfo.aptStatus
            if (appointmentInfo.aptStatus == "Confirmed") {
                binding.ivStatus.setImageResource(R.drawable.ic_baseline_check_circle_24)
            } else if (appointmentInfo.aptStatus == "Rescheduled") {
                binding.ivStatus.setImageResource(R.drawable.ic_baseline_check_circle_24)
            } else {
                binding.ivStatus.setImageResource(R.drawable.ic_baseline_cancel_24)
            }
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, AppointmentInfoActivity::class.java)
                intent.putExtra(APPOINTMENT_DETAIL, appointmentInfo.toAppointment())
                intent.putExtra(APPOINTMENT_ID,appointmentInfo.aptNo)
                binding.root.context.startActivity(intent)
            }


        }
    }
}
