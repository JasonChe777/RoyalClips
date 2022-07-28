package com.example.royalclips.model.data.getBarberServices

import com.google.gson.annotations.SerializedName

data class GetBarberServiceRequest(
    @SerializedName("barberId")
    val barberId: Int,

    @SerializedName("hasDefaultServices")
    val hasDefaultServices: Int,
)
