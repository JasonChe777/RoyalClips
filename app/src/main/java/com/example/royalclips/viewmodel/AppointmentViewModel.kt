package com.example.royalclips.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.royalclips.model.data.bookAppointments.Appointment
import com.example.royalclips.model.data.bookAppointments.BookResponse
import com.example.royalclips.model.data.getAppointments.AppointmentInfo
import com.example.royalclips.model.data.getAppointments.GetAppointmentsResponse
import com.example.royalclips.model.data.login.LoginResponse
import com.example.royalclips.model.remote.ApiClient
import com.example.royalclips.model.remote.ApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentViewModel : ViewModel() {
    private val retrofit = ApiClient.getRetrofit()

    val appointmentLiveData = MutableLiveData<Appointment>()
    val appointmentInfoLiveData = MutableLiveData<ArrayList<AppointmentInfo>>()
    val cancelSuccess = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    private val apiService = retrofit.create(ApiService::class.java)

    fun bookAppointment(apiToken: String, map: HashMap<String, Any>) {
        loadingLiveData.postValue(true)
        val reqJson: String = Gson().toJson(map)
        val body: RequestBody =
            reqJson.toRequestBody("application/json".toMediaTypeOrNull())
        val call: Call<BookResponse> = apiService.bookAppointment(apiToken, body)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        Log.e("loadCurrentAppointments", response.body()!!.appointment.toString())
                        appointmentLiveData.postValue(response.body()!!.appointment)

                    } else {
                        Log.e("response error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun rescheduleAppointment(apiToken: String, map: HashMap<String, Any>) {
        val reqJson: String = Gson().toJson(map)
        val body: RequestBody =
            reqJson.toRequestBody("application/json".toMediaTypeOrNull())
        val call: Call<BookResponse> =
            apiService.rescheduleAppointment(apiToken, body)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        Log.e("loadCurrentAppointments", response.body()!!.appointment.toString())
                        appointmentLiveData.postValue(response.body()!!.appointment)

                    } else {
                        Log.e("response error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun cancelAppointment(apiToken: String, appointmentId: Int) {
        val url = "appointment/cancelAppointment/$appointmentId"
        val call: Call<BookResponse> = apiService.cancelAppointment(url, apiToken)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        cancelSuccess.postValue(true)

                    } else {
                        Log.e("response error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun getAppointments(userId: Int, apiToken: String) {
        loadingLiveData.postValue(true)
        val url = "appointment/myAppointments/$userId"
        val call: Call<GetAppointmentsResponse> =
            apiService.getAppointments(url, apiToken)
        call.enqueue(object : Callback<GetAppointmentsResponse> {
            override fun onResponse(
                call: Call<GetAppointmentsResponse>,
                response: Response<GetAppointmentsResponse>
            ) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        Log.e("loadCurrentAppointments", response.body()!!.appointments.toString())
                        appointmentInfoLiveData.postValue(response.body()!!.appointments)

                    } else {
                        Log.e("response error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetAppointmentsResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun getAppointmentDetail(apiToken: String, appointmentId: Int) {
        loadingLiveData.postValue(true)
        val url = "appointment/getAppointmentDetail/$appointmentId"
        val call: Call<BookResponse> =
            apiService.getAppointmentDetail(url, apiToken)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        Log.e("loadCurrentAppointments", response.body()!!.appointment.toString())
                        appointmentLiveData.postValue(response.body()!!.appointment)

                    } else {
                        Log.e("response error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("response.body()", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun initAppointment(appointment: Appointment) {
        appointmentLiveData.postValue(appointment)
    }
}