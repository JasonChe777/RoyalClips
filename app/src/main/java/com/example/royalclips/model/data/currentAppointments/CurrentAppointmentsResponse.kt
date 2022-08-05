package com.example.royalclips.model.data.currentAppointments


data class CurrentAppointmentsResponse(
    val message: String,
    val slots: ArrayList<Slot>,
    val status: Int
)