package com.example.royalclips.model.data.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("fcmToken")
    val fcmToken: String,

    @SerializedName("mobileNo")
    val mobileNo: String,

    @SerializedName("password")
    val password: String,
)
