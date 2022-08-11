package com.example.royalclips.model.data.getServiceCategory

data class GetServiceCategoryResponse(
    val message: String,
    val serviceCategories: ArrayList<ServiceCategory>,
    val status: Int
)