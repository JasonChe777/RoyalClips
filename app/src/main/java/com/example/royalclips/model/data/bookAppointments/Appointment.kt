package com.example.royalclips.model.data.bookAppointments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointment(
    val aptDate: String?=  null,
    val aptNo: Int,
    val aptStatus: String?=  null,
    val barberId: Int?=  null,
    val barberName: String?=  null,
    val couponCode: String?=  null,
    val couponDiscount: Double?=  null,
    val finalCost: Double?=  null,
    val fullName: String?=  null,
    val mobileNo: String?=  null,
    val previousTimePhotos: String?=  null,
    val profilePic: String?=  null,
    val services: List<Service> = listOf(),
    val timeFrom: String?=  null,
    val timeTo: String?=  null,
    val totalCost: Double?=  null,
    val totalDuration: Double?=  null,
    val userId: Int?=  null,
    val userProfilePic: String?=  null
):Parcelable