package com.example.royalclips.model.data.getAppointments

data class GetAppointmentsResponse(
    val appointments: ArrayList<AppointmentInfo>,
    val message: String,
    val status: Int
)