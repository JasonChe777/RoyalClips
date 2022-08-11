package com.example.royalclips.model.data.workingHour

data class WorkingHourResponse(
    val message: String,
    val status: Int,
    val timings: Map<String, Weekday>
)