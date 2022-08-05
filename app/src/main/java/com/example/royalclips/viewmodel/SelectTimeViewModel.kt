package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.currentAppointments.CurrentAppointmentsResponse
import com.example.royalclips.model.data.currentAppointments.Slot
import com.example.royalclips.model.data.getBarberServices.Service
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.math.roundToInt

class SelectTimeViewModel : ViewModel() {
    private val retrofit: Retrofit = ApiClient.getRetrofit()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    val currentAppointmentsLiveData = MutableLiveData<ArrayList<Slot>>()
    val barberServicesIdLiveData = MutableLiveData<Int>()
    val appointmentsSlotLiveData = MutableLiveData<Int>()
    val appointmentsDateLiveData = MutableLiveData<String>()
    val appointmentsStartFromLiveData = MutableLiveData<Int>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun loadCurrentAppointments() {
        loadingLiveData.postValue(true)
        val url = "appointment/currentAppointments/" + barberServicesIdLiveData.value
        val call: Call<CurrentAppointmentsResponse> = apiService.currentAppointments(url)
        call.enqueue(object : Callback<CurrentAppointmentsResponse> {
            override fun onResponse(
                call: Call<CurrentAppointmentsResponse>,
                response: Response<CurrentAppointmentsResponse>
            ) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        Log.e("loadCurrentAppointments", response.body()!!.slots.toString())
                        currentAppointmentsLiveData.postValue(response.body()!!.slots)

                    } else {
                        Log.e("response error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<CurrentAppointmentsResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }


}