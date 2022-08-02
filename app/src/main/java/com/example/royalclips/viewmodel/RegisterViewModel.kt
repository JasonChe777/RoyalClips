package com.example.royalclips.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import com.example.royalclips.model.data.register.RegisterRequest
import com.example.royalclips.model.data.register.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RegisterViewModel: ViewModel(){

    val goToLogin = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService



    fun doRegister(registerRequest: RegisterRequest){
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)

        val registerApiCall: Call<RegisterResponse> = apiService.doRegister(registerRequest)
        registerApiCall.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                when (response.body()?.status) {
                    0 -> {
                        goToLogin.postValue(true)
                    }
                    1 -> error.postValue(response.body()?.message)
                    else -> {
                        error.postValue(response.body()?.message)
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                error.postValue(t.message)
                t.printStackTrace()
            }
        })
    }
}