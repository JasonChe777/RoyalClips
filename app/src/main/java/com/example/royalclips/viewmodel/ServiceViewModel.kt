package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.getServiceCategory.GetServiceCategoryResponse
import com.example.royalclips.model.data.getServiceCategory.ServiceCategory
import com.example.royalclips.model.data.getServicesByCategory.GetServicesByCategoryResponse
import com.example.royalclips.model.data.getServicesByCategory.ServiceDetail
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ServiceViewModel: ViewModel() {
    private val retrofit: Retrofit = ApiClient.getRetrofit()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    val serviceCategoriesLiveData = MutableLiveData<ArrayList<ServiceCategory>>()
    val serviceCategoryIdLiveData = MutableLiveData<Int>()
    val servicesListLiveData = MutableLiveData<ArrayList<ServiceDetail>>()
    val serviceLiveData = MutableLiveData<ServiceDetail>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getServiceCategories() {
        loadingLiveData.postValue(true)
        val call: Call<GetServiceCategoryResponse> = apiService.getServiceCategory()
        call.enqueue(object : Callback<GetServiceCategoryResponse> {
            override fun onResponse(
                call: Call<GetServiceCategoryResponse>,
                response: Response<GetServiceCategoryResponse>
            ) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {

                        serviceCategoriesLiveData.postValue(response.body()!!.serviceCategories)

                    } else {
                        Log.e("getDashboard error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetServiceCategoryResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun getServicesByCategory(categoryId: Int) {
        loadingLiveData.postValue(true)
        val url = "service/category/$categoryId"
        val call: Call<GetServicesByCategoryResponse> = apiService.getServicesByCategory(url)
        call.enqueue(object : Callback<GetServicesByCategoryResponse> {
            override fun onResponse(
                call: Call<GetServicesByCategoryResponse>,
                response: Response<GetServicesByCategoryResponse>
            ) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {

                        servicesListLiveData.postValue(response.body()!!.services)

                    } else {
                        Log.e("getDashboard error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetServicesByCategoryResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }
}