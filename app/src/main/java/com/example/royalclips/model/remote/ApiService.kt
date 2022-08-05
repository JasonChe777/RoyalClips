package com.example.royalclips.model.remote


import com.example.royalclips.model.Constants.LOGIN_END_POINT
import com.example.royalclips.model.Constants.REGISTER_END_POINT
import com.example.royalclips.model.data.currentAppointments.CurrentAppointmentsResponse
import com.example.royalclips.model.data.getBarber.GetBarberResponse
import com.example.royalclips.model.data.getBarberServices.GetBarberServiceResponse
import com.example.royalclips.model.data.login.LoginRequest
import com.example.royalclips.model.data.login.LoginResponse
import com.example.royalclips.model.data.register.RegisterRequest
import com.example.royalclips.model.data.register.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-type:application/json")
    @POST(LOGIN_END_POINT)
    fun doLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>


    @Headers("Content-type:application/json")
    @POST(REGISTER_END_POINT)
    fun doRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @Headers("Content-type:application/json")
    @GET("barber/getBarbers")
    fun getBarbers(): Call<GetBarberResponse>

    @Headers("Content-type:application/json")
    @POST("barber/getBarberServices1")
    fun getBarberServices(@Body getBarberServicesReq: RequestBody): Call<GetBarberServiceResponse>

    @Headers("Content-type: application/json")
    @GET
    fun currentAppointments(@Url url: String): Call<CurrentAppointmentsResponse>

//    @Headers("Content-type: application/json")
//    @POST("/appointment/book")
//    fun bookAppointment(@Header("ps_auth_token") ps_auth_token: String, @Body bookReq: RequestBody): Call<BookResponse>
//
//    @Headers("Content-type: application/json")
//    @POST("/appointment/reschedule")
//    fun rescheduleAppointment(@Header("ps_auth_token") ps_auth_token: String, @Body bookReq: RequestBody): Call<BookResponse>
//
//    @Headers("Content-type: application/json")
//    @GET
//    fun cancelAppointment(@Url url: String, @Header("ps_auth_token") ps_auth_token: String): Call<BookResponse>
//
//    @Headers("Content-type: application/json")
//    @GET
//    fun getAppointments(@Url url: String, @Header("ps_auth_token") ps_auth_token: String): Call<GetAppoitmentsResponse>
//
//    @Headers("Content-type: application/json")
//    @GET
//    fun getAppointmentDetail(@Url url: String, @Header("ps_auth_token") ps_auth_token: String): Call<BookResponse>
}

