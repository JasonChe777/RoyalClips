package com.example.royalclips.model.data.getBarberServices

import com.example.royalclips.model.data.bookAppointments.Service

data class GetBarberServiceResponse(
    val message: String,
    val services: ArrayList<Service>,
    val status: Int
)