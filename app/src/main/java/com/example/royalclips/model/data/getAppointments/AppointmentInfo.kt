package com.example.royalclips.model.data.getAppointments

import com.example.royalclips.model.data.bookAppointments.Appointment

data class AppointmentInfo(
    val aptDate: String,
    val aptNo: Int,
    val aptStatus: String,
    val timeFrom: String,
    val timeTo: String,
    val totalDuration: Double
)

internal fun  AppointmentInfo.toAppointment(): Appointment {
    return Appointment(
        aptDate = aptDate,
        aptNo =  aptNo,
        aptStatus = aptStatus,
        timeFrom = timeFrom,
        timeTo = timeTo,
        totalDuration =  totalDuration
    )

}