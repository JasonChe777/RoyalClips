package com.example.royalclips.model.data.getServicesByCategory

data class GetServicesByCategoryResponse(
    val message: String,
    val services: ArrayList<ServiceDetail>,
    val status: Int
)