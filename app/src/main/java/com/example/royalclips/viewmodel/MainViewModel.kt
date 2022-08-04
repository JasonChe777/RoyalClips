package com.example.royalclips.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.login.LoginResponse

class MainViewModel: ViewModel() {
    val userLiveData = MutableLiveData<LoginResponse>()

    fun setUserLiveData(loginResponse: LoginResponse){
        userLiveData.postValue(loginResponse)
    }
}