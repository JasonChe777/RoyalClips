package com.example.royalclips.model.data.getBarber

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Barber(
    val barberId: Int,
    val barberName: String,
    val breakTimeFrom: String?= null,
    val breakTimeTo: String? = null,
    val gender: String? = null,
    val hasDefaultServices: Int? = null,
    val holiday: String? = null,
    val isAdmin: Int? = null,
    val isBarber: Int? = null,
    val mobileNo: String? = null,
    val password: String? = null,
    val payment: Double? = null,
    val profilePic: String,
    val type: String? = null,
    val userRating: Double? = null
):Parcelable