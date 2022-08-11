package com.example.royalclips.model.data.getServicesByCategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceDetail(
    val cost: Double,
    val description: String,
    val duration: Double,
    val serviceId: Int,
    val serviceName: String,
    val servicePic: String,
    val serviceType: String
):Parcelable