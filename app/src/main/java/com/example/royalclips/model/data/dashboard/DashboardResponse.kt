package com.example.royalclips.model.data.dashboard

data class DashboardResponse(
    val alertMessage: String,
    val banners: List<Banner>,
    val isShopOpened: String,
    val message: String,
    val status: Int
)