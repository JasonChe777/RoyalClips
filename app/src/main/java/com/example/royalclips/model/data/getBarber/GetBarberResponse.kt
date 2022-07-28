package com.example.royalclips.model.data.getBarber

data class GetBarberResponse(
    val barbers: List<Barber>,
    val status: Int
)