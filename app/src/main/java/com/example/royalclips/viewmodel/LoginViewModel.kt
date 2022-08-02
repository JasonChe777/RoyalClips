package com.example.royalclips.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.login.LoginRequest
import com.example.royalclips.model.data.login.LoginResponse
import com.example.royalclips.model.data.register.RegisterRequest
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginViewModel: ViewModel() {
    val goToHomeScreen = MutableLiveData<Boolean>()
    val loginLiveData = MutableLiveData<LoginResponse>()
    val error = MutableLiveData<String>()

    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService



    fun doLogin(loginRequest: LoginRequest){
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)


        val loginApiCall: Call<LoginResponse> = apiService.doLogin(loginRequest)
        loginApiCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                when (response.body()?.status) {
                    0 -> {
                        loginLiveData.postValue(response.body())
                        goToHomeScreen.postValue(true)

                    }
                    1 -> {
                        error.postValue(response.body()?.message)
                    }
                    else -> {
                        error.postValue(response.body()?.message)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                error.postValue(t.message)
                t.printStackTrace()
            }
        })
    }

}