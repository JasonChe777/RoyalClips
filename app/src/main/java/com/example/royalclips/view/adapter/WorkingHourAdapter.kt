package com.example.royalclips.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.royalclips.R
import com.example.royalclips.databinding.ItemWorkingHourBinding
import com.example.royalclips.model.data.workingHour.Weekday
import com.example.royalclips.view.WorkingHourActivity
import com.example.royalclips.viewmodel.MainViewModel

class WorkingHourAdapter(private val context: Context, private val infoMap: Map<String, Weekday>) :
    RecyclerView.Adapter<WorkingHourAdapter.WorkingHourHolder>() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ItemWorkingHourBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkingHourHolder {
        binding = ItemWorkingHourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(context as WorkingHourActivity)[MainViewModel::class.java]
        return WorkingHourHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkingHourHolder, position: Int) {
        holder.apply {
            val day = infoMap.keys.toList()[position]
            val weekday = infoMap[day]
            holder.bind(day, weekday!!)
            if (position == 0){
                binding.tvDay.setTextColor(context.resources.getColor(R.color.purple_200))
                binding.tvTime.setTextColor(context.resources.getColor(R.color.purple_200))
            }
        }
    }

    override fun getItemCount(): Int {
        return infoMap.size
    }


    inner class WorkingHourHolder(val binding: ItemWorkingHourBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(day: String, weekday: Weekday) {
            var time = "${weekday.fromTime} to ${weekday.toTime}"
            if(weekday.fromTime == weekday.toTime){
                time = "Holiday"
                binding.tvTime.setTextColor(context.resources.getColor(R.color.purple_500))
            }
            binding.tvDay.text = day
            binding.tvTime.text = time

        }
    }
}