package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.currentAppointments.CurrentAppointmentsResponse
import com.example.royalclips.model.data.currentAppointments.Slot
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SelectTimeViewModel : ViewModel() {
    private val retrofit: Retrofit = ApiClient.getRetrofit()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    val currentAppointmentsLiveData = MutableLiveData<ArrayList<Slot>>()
    val appointmentsDateLiveData = MutableLiveData<String>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val startFromSlotLiveData =  MutableLiveData<Int>(-1)
    val slotNumberLiveDate = MutableLiveData<Int>()

    fun loadCurrentAppointments(barberId:Int) {
        loadingLiveData.postValue(true)
        val url = "appointment/currentAppointments/$barberId"
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

                        appointmentsDateLiveData.postValue(response.body()!!.slots[0].date)

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

    fun setSlotNumber(slotNumber:Int){
       slotNumberLiveDate.postValue(slotNumber)
    }

    fun getFromAndToTimeString():ArrayList<String>{
        var fromTimeString:String = ""
        var toTimeString :String = ""
        currentAppointmentsLiveData.value!!.forEach() {
            if (it.date == appointmentsDateLiveData.value) {
                fromTimeString = it.slots.keys.elementAt(startFromSlotLiveData.value!!).split("-")[0]
                toTimeString = it.slots.keys.elementAt(startFromSlotLiveData.value!!+ slotNumberLiveDate.value!! - 1).split("-")[1]
            }
        }
        return arrayListOf(fromTimeString,toTimeString)
    }

}