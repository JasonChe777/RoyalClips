package com.example.royalclips.model.data.contacts

data class Contact(
    val contactData: String,
    val contactId: Int,
    val contactTitle: String,
    val contactType: String,
    val displayOrder: Int,
    val iconUrl: String
)