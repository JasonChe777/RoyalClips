package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.royalclips.model.data.BaseResponse
import com.example.royalclips.model.data.getPhoneVerificationCode.GetPhoneVerificationCodeResponse
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

class ResetPasswordViewModel : ViewModel() {
    val retrofit: Retrofit = ApiClient.getRetrofit()
    val apiService: ApiService = retrofit.create(ApiService::class.java)
    val error = MutableLiveData<String>()
    val success = MutableLiveData<String>()
    val code = MutableLiveData<String>()
    val goToLogin = MutableLiveData<Boolean>()

    private val verificationCodeResponse = MutableStateFlow<GetPhoneVerificationCodeResponse?>(null)
    private val resetPasswordResponse = MutableStateFlow<BaseResponse?>(null)

    fun getPhoneVerificationCode(mobileNo: String) = viewModelScope.launch{
        val url = "appUser/getPhoneVerificationCode/$mobileNo"
        verificationCodeResponse.emit(apiService.getPhoneVerificationCode(url))
        Log.e("", verificationCodeResponse.value.toString())
        if(verificationCodeResponse.value!!.status == 1){
            error.postValue(verificationCodeResponse.value!!.message)
        }else{
            code.postValue(verificationCodeResponse.value!!.opt)
        }
    }

    fun resetPassword(mobileNo: String, password: String, phoneVerificationCode: String) = viewModelScope.launch{
        val map = HashMap<String, String>()
        map["mobileNo"] = mobileNo
        map["password"] = password
        map["phoneVerificationCode"] = phoneVerificationCode

        val reqJson: String = Gson().toJson(map)
        val body: RequestBody =
            reqJson.toRequestBody("application/json".toMediaTypeOrNull())
        resetPasswordResponse.emit(apiService.resetPassword(body))
        Log.e("", resetPasswordResponse.value.toString())
        if(resetPasswordResponse.value!!.status == 1){
            error.postValue(resetPasswordResponse.value!!.message)
        } else {
            success.postValue(resetPasswordResponse.value!!.message)
            goToLogin.postValue(true)
        }

    }
}