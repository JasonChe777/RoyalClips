package com.example.royalclips.model.data.getBarberServices

data class GetBarberServiceResponse(
    val message: String,
    val services: ArrayList<Service>,
    val status: Int
)