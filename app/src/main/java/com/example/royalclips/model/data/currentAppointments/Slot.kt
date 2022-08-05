package com.example.royalclips.model.data.currentAppointments

data class Slot(
    val date: String,
    val day: String,
    val slots: Map<String, Boolean>
)