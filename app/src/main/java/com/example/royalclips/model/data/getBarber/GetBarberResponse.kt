package com.example.royalclips.model.data.getBarber

data class GetBarberResponse(
    val barbers: ArrayList<Barber>,
    val message: String,
    val status: Int
)