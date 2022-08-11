package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.login.LoginRequest
import com.example.royalclips.model.data.login.LoginResponse
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginViewModel: ViewModel() {
    val goToHomeScreen = MutableLiveData<LoginResponse>()
    val userLiveData = MutableLiveData<LoginResponse>()
    val error = MutableLiveData<String>()
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

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
                        goToHomeScreen.postValue(response.body())

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

    fun updateFcmToken (fcmToken: String) {
        val token = userLiveData.value!!.apiToken
        val map = HashMap<String, Any>()
        map["userId"] = userLiveData.value!!.userId
        map["fcmToken"] = fcmToken
        map["application"] = "Hongjia"
        val reqJson: String = Gson().toJson(map)
        val body: RequestBody =
            reqJson.toRequestBody("application/json".toMediaTypeOrNull())
        compositeDisposable.add(apiService.updateFcmToken(token, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                { t: Throwable? -> Log.i("Throwable", t?.message ?: "error") }
            )

        )
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}