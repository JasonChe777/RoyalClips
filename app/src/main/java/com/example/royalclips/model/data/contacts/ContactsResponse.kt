package com.example.royalclips.model.data.contacts

data class ContactsResponse(
    val contacts: ArrayList<Contact>,
    val message: String,
    val status: Int
)