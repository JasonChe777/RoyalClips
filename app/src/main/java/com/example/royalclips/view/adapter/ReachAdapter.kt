package com.example.royalclips.view.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.royalclips.R
import com.example.royalclips.databinding.ItemHowToReachBinding
import com.example.royalclips.model.Constants
import com.example.royalclips.model.data.contacts.Contact
import com.example.royalclips.view.ReachActivity
import com.example.royalclips.viewmodel.MainViewModel


class ReachAdapter(private val context: Context, private val infoList: ArrayList<Contact>) :
    RecyclerView.Adapter<ReachAdapter.ContactHolder>() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ItemHowToReachBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        binding =  ItemHowToReachBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel =  ViewModelProvider(context as ReachActivity)[MainViewModel::class.java]
        return ContactHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.apply {
            val info = infoList[position]
            holder.bind(info)
        }
    }

    override fun getItemCount(): Int {
        return infoList.size
    }


    inner class ContactHolder(val binding: ItemHowToReachBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.tvContactTitle.text = contact.contactTitle
            binding.tvContactDetails.text = contact.contactData
            if (contact.contactType == "PHONE") {
                binding.ivBynIcon1.setImageResource(R.drawable.ic_baseline_phone_24)
                binding.ivBynIcon2.visibility = View.VISIBLE
                binding.ivBynIcon2.setImageResource(R.drawable.ic_baseline_textsms_24)
                Glide.with(context)
                    .load(Constants.BASE_IMAGE_URL + contact.iconUrl)
                    .into(binding.ivIcon)
                binding.ivBynIcon1.setOnClickListener {
                    val uri: Uri = Uri.parse("tel:" + contact.contactData)
                    val intent = Intent(Intent.ACTION_DIAL, uri)
                    context.startActivity(intent)
                }
                binding.ivBynIcon2.setOnClickListener {
                    val uri: Uri = Uri.parse("smsto:" + contact.contactData)
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    context.startActivity(intent)
                }
            }
            if (contact.contactType == "EMAIL") {
                binding.ivBynIcon1.setImageResource(R.drawable.ic_baseline_email_24)
                Glide.with(context)
                    .load(Constants.BASE_IMAGE_URL + contact.iconUrl)
                    .into(binding.ivIcon)
                binding.ivBynIcon1.setOnClickListener {

                    val intent = Intent()
                    intent.action = "android.intent.action.SEND"
                    intent.putExtra(
                        Intent.EXTRA_EMAIL,
                        contact.contactData
                    )
                    intent.type = "text/plain"
                    context.startActivity(intent)
                }
            }
            if (contact.contactType == "GEO") {
                val arrayOfString: List<String> = contact.contactData.split("###")
                val latitude = arrayOfString[0].toDouble()
                val longitude = arrayOfString[1].toDouble()
                binding.tvContactDetails.text = arrayOfString[2]
                binding.ivBynIcon1.setImageResource(R.drawable.ic_baseline_location_on_24)
                Glide.with(context)
                    .load(Constants.BASE_IMAGE_URL + contact.iconUrl)
                    .into(binding.ivIcon)
                binding.ivBynIcon1.setOnClickListener {
                    val stringBuilder = StringBuilder()
                    stringBuilder.append("geo:")
                    stringBuilder.append(latitude)
                    stringBuilder.append(",")
                    stringBuilder.append(longitude)
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(stringBuilder.toString()))
                    context.startActivity(intent)
                }
            }
            if (contact.contactType == "WEB_LINK") {
                binding.tvContactDetails.setTextColor(context.resources.getColor(R.color.teal_200))
                binding.ivBynIcon1.visibility = View.GONE
                Glide.with(context)
                    .load(Constants.BASE_IMAGE_URL + contact.iconUrl)
                    .into(binding.ivIcon)
                binding.tvContactDetails.setOnClickListener {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(contact.contactData))
                    context.startActivity(intent)
                }
            }
            if (contact.contactType == "TEXT") {
                binding.ivIcon.visibility = View.GONE
                binding.ivBynIcon1.visibility = View.GONE
            }

        }
    }
}
