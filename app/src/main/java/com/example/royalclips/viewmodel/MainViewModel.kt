package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.royalclips.model.data.BaseResponse
import com.example.royalclips.model.data.contacts.Contact
import com.example.royalclips.model.data.dashboard.DashboardResponse
import com.example.royalclips.model.data.login.LoginResponse
import com.example.royalclips.model.data.workingHour.Weekday
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.HashMap

class MainViewModel : ViewModel() {
    private val retrofit: Retrofit = ApiClient.getRetrofit()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    var logoutResponse = MutableLiveData<BaseResponse>()
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    val workingHourLiveData = MutableLiveData<Map<String, Weekday>>()
    val dashboardLiveData = MutableLiveData<DashboardResponse>()
    val contactsLiveData = MutableLiveData<ArrayList<Contact>>()
    val loadingLiveData = MutableLiveData<Boolean>()


    fun logout(apiToken: String, map: HashMap<String, Any>) {
        val reqJson: String = Gson().toJson(map)
        val body: RequestBody =
            reqJson.toRequestBody("application/json".toMediaTypeOrNull())
        compositeDisposable.add(apiService.logout(apiToken, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    logoutResponse.postValue(response)
                },
                { t: Throwable? -> Log.i("Throwable", t?.message ?: "error") }
            )
        )
    }

    fun getWorkingHour() {
        compositeDisposable.add(apiService.getWorkingHours()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val workingHour = it.timings.entries.sortedBy { getWeekOfDate(it.key) }
                        .associateBy({ it.key }, { it.value })
                    workingHourLiveData.postValue(workingHour)
                },
                { t: Throwable? -> Log.i("Throwable", t?.message ?: "error") }
            )

        )
    }

    private fun getWeekOfDate(day: String): Int {
        val weekDays =
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val intDay = weekDays.indexOf(day)
        val cal = Calendar.getInstance()
        var w = cal[Calendar.DAY_OF_WEEK] - 1
        println((intDay + 7 - w) % 7)
        if (w < 0) {
            w = 0
        }
        return (intDay + 7 - w) % 7
    }


    fun getDashboard() {
        loadingLiveData.postValue(true)
        val call: Call<DashboardResponse> = apiService.dashboard()
        call.enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        dashboardLiveData.postValue(response.body()!!)
                    } else {
                        Log.e("getDashboard error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun getContacts() {
        loadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getContacts()
                loadingLiveData.postValue(false)
                if (!response.isSuccessful) {
                    return@launch
                }
                response.body()?.let {
                    val contacts: ArrayList<Contact> = it.contacts
                    contacts.sortBy { it.displayOrder }
                    contactsLiveData.postValue(contacts)
                }
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}