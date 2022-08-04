package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.getBarberServices.GetBarberServiceResponse
import com.example.royalclips.model.data.getBarberServices.Service
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SelectServiceViewModel:ViewModel() {

    private val retrofit: Retrofit = ApiClient.getRetrofit()
    private val barberApiService: ApiService = retrofit.create(ApiService::class.java)
    val barberServicesIdLiveData = MutableLiveData<Int>()
    val barberServicesLiveData = MutableLiveData<ArrayList<Service>>()
    val barberServicesSelectLiveData = MutableLiveData<ArrayList<Int>>()
    val barberServicesTypeLiveData = MutableLiveData<ArrayList<String>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getServicesByBarber(barberId: Int){
        loadingLiveData.postValue(true)
        if(barberServicesIdLiveData.value == null || barberId != barberServicesIdLiveData.value){
            val map = HashMap<String, String>()
            map["barber_id"] = barberId.toString()
            val reqJson: String = Gson().toJson(map)
            val body: RequestBody =
                reqJson.toRequestBody("application/json".toMediaTypeOrNull())
            val call: Call<GetBarberServiceResponse> = barberApiService.getBarberServices(body)
            call.enqueue(object : Callback<GetBarberServiceResponse> {
                override fun onResponse(call: Call<GetBarberServiceResponse>, response: Response<GetBarberServiceResponse>) {
                    loadingLiveData.postValue(false)
                    if (response.isSuccessful) {
                        if(response.body()!!.status == 0){
                            barberServicesIdLiveData.postValue(barberId)
                            val serviceType = ArrayList<String>()
                            for (service in response.body()!!.services){
                                if(service.serviceType !in serviceType){
                                    serviceType.add(service.serviceType)
                                }
                            }
                            barberServicesLiveData.postValue(response.body()!!.services)
                            barberServicesSelectLiveData.postValue(ArrayList())
                            barberServicesTypeLiveData.postValue(serviceType)
                        } else {
                            Log.e("response error", response.body()!!.message)
                        }
                    }
                }
                override fun onFailure(call: Call<GetBarberServiceResponse>, t: Throwable) {
                    Log.e("response.body()", t.toString())
                    t.printStackTrace()
                }
            })

        }
    }
}