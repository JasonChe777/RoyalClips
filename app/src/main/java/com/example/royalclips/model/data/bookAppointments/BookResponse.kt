package com.example.royalclips.model.data.bookAppointments

data class BookResponse(
    val appointment: Appointment,
    val message: String,
    val status: Int
)