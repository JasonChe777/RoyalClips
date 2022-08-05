package com.example.royalclips.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.royalclips.R
import com.example.royalclips.databinding.ItemSelectDateBinding
import com.example.royalclips.model.data.currentAppointments.Slot
import com.example.royalclips.view.SelectTimeActivity
import com.example.royalclips.viewmodel.SelectTimeViewModel


class SelectDateAdapter(private val context: Context, private val infoList: List<Slot>) :
    RecyclerView.Adapter<SelectDateAdapter.SelectDateHolder>() {
    lateinit var viewModel: SelectTimeViewModel
    lateinit var binding: ItemSelectDateBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDateHolder {
        binding = ItemSelectDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(context as SelectTimeActivity)[SelectTimeViewModel::class.java]
        return SelectDateHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectDateHolder, position: Int) {
        holder.apply {
            val info = infoList[position]
            holder.bind(info)
        }
    }

    override fun getItemCount(): Int {
        return infoList.size
    }


    inner class SelectDateHolder(val binding: ItemSelectDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(slot: Slot) {
            binding.tvDate.text = slot.date
            binding.tvDay.text = slot.day
            viewModel.appointmentsDateLiveData.observe(context as SelectTimeActivity) {
                if (slot.date == it) {
                    binding.llWrapper.setBackgroundColor(
                        ContextCompat.getColor(
                           context,
                            R.color.teal_700
                        )
                    )
                } else {
                    binding.llWrapper.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.loginBtnColor
                        )
                    )
                }
            }
            binding.root.setOnClickListener {
                viewModel.appointmentsDateLiveData.postValue(slot.date)
            }
        }
    }
}
