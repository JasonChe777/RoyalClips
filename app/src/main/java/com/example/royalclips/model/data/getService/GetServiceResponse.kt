package com.example.royalclips.model.data.getService

import com.example.royalclips.model.data.bookAppointments.Service

data class GetServiceResponse(
    val message: String,
    val services: ArrayList<Service>,
    val status: Int
)