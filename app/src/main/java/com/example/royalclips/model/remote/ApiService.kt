package com.example.royalclips.model.remote


import com.example.royalclips.model.Constants.LOGIN_END_POINT
import com.example.royalclips.model.Constants.REGISTER_END_POINT
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
}

