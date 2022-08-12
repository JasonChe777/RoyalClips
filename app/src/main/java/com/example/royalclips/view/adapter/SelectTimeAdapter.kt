package com.example.royalclips.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.royalclips.R
import com.example.royalclips.databinding.ItemSelectTimeBinding
import com.example.royalclips.viewmodel.SelectTimeViewModel

class SelectTimeAdapter(
    private val context: Context,
    private val infoMap: Map<String, Boolean>,
    private val viewModel:SelectTimeViewModel,
    private val slotNumber:Int
) :
    RecyclerView.Adapter<SelectTimeAdapter.SelectDateHolder>() {
    lateinit var binding: ItemSelectTimeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDateHolder {
        binding = ItemSelectTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectDateHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectDateHolder, position: Int) {
        holder.apply {
            val info = infoMap.keys.elementAt(position)
            holder.bind(info, infoMap[info]!!, position)
        }
    }

    override fun getItemCount(): Int {
        return infoMap.size
    }

    fun freeSlots(slots: Int, position: Int): Int {
        for (i in 0 until slots) {
            if (position + i >= infoMap.size || infoMap[infoMap.keys.elementAt(position + i)] == true) {
                return i
            }
        }
        return -1
    }


    inner class SelectDateHolder(val binding: ItemSelectTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(time: String, booked: Boolean, position: Int) {
            binding.tvTimeSlot.text = time.split("-")[0]
            if (booked) {
                binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_booked)
            } else {
                binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_available)
            }
            binding.tvTimeSlot.setOnClickListener {
                val slots = slotNumber
                val freeSlots = freeSlots(slots, position)
                if (freeSlots == -1) {
                   viewModel.startFromSlotLiveData.postValue(position)

                } else {
                    val builder = AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Required time slots are $slots. \nOnly $freeSlots slot is available within your time slot selection.")
                        .setPositiveButton("Select Again") { _, _ ->
                        }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(true)
                    alertDialog.show()
                }
            }

            viewModel.startFromSlotLiveData.observe(context as AppCompatActivity) {

                if ( it != -1 && position in it until (it + slotNumber)) {
                    binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_selected)
                } else {
                    if (booked) {
                        binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_booked)
                    } else {
                        binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_available)
                    }
                }
            }

        }
    }
}