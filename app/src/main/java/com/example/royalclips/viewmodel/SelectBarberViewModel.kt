package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.getBarber.Barber
import com.example.royalclips.model.data.getBarber.GetBarberResponse
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SelectBarberViewModel: ViewModel() {

    val barbersLiveData = MutableLiveData<ArrayList<Barber>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService



    fun getBarbers(){
         loadingLiveData.postValue(true)

        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)

        if(barbersLiveData.value == null){
            val call: Call<GetBarberResponse> =apiService.getBarbers()
            call.enqueue(object : Callback<GetBarberResponse> {
                override fun onResponse(call: Call<GetBarberResponse>, response: Response<GetBarberResponse>) {
                    loadingLiveData.postValue(false)
                    if (response.isSuccessful) {
                        if(response.body()?.status == 0){
                            barbersLiveData.postValue(response.body()?.barbers)
                        } else {
                            Log.e("response error", response.body()!!.message)
                        }
                    }
                }
                override fun onFailure(call: Call<GetBarberResponse>, t: Throwable) {
                    loadingLiveData.postValue(false)
                    Log.e("response.body()", t.toString())
                    t.printStackTrace()
                }
            })

        }
    }
}