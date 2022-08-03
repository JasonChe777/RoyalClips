package com.example.royalclips.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.royalclips.databinding.ItemSelectBarberBinding
import com.example.royalclips.model.Constants.BASE_IMAGE_URL
import com.example.royalclips.model.data.getBarber.Barber


class SelectBarberAdapter(
    private val context: Context,
    private val infoArrayList: ArrayList<Barber>
) :
    RecyclerView.Adapter<SelectBarberAdapter.BarberHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarberHolder {
        val binding =
            ItemSelectBarberBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BarberHolder(binding)
    }

    override fun onBindViewHolder(holder: BarberHolder, position: Int) {
        holder.apply {
            val info = infoArrayList[position]
            holder.bind(info)
        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }


    inner class BarberHolder(private val binding: ItemSelectBarberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(barber: Barber) {
            binding.tvBarberName.text = barber.barberName
            binding.rbRating.rating = barber.userRating.toFloat()
            Glide.with(context)
                .load(BASE_IMAGE_URL + barber.profilePic)
                .into(binding.ivProfilePic)
        }
    }
}
