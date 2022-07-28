package com.example.royalclips.model.remote

import com.example.royalclips.model.Constants.GET_BARBERS_END_POINT
import com.example.royalclips.model.Constants.GET_SERVICES_END_POINT
import com.example.royalclips.model.Constants.GET_SERVICES_OF_BARBER_END_POINT
import com.example.royalclips.model.Constants.LOGIN_END_POINT
import com.example.royalclips.model.Constants.REGISTER_END_POINT
import com.example.royalclips.model.data.login.LoginResponse
import com.example.royalclips.model.data.getBarber.GetBarberResponse
import com.example.royalclips.model.data.getBarberServices.GetBarberServiceRequest
import com.example.royalclips.model.data.getBarberServices.GetBarberServiceResponse
import com.example.royalclips.model.data.getService.GetServiceResponse
import com.example.royalclips.model.data.register.RegisterRequest
import com.example.royalclips.model.data.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @Headers("Content-type:application/json")
    @POST(LOGIN_END_POINT)
    fun doLogin(
        @Field("username") mobileNo: String,
        @Field("password") password: String
    ): Call<LoginResponse>



    @Headers("Content-type:application/json")
    @POST(REGISTER_END_POINT)
    fun doRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET(GET_SERVICES_END_POINT)
    fun getServices(): Call<GetServiceResponse>

    @GET(GET_BARBERS_END_POINT)
    fun getBarbers(): Call<GetBarberResponse>


    @Headers("Content-type:application/json")
    @POST(GET_SERVICES_OF_BARBER_END_POINT)
    fun getBarberServices(
        @Body getBarberServiceRequest: GetBarberServiceRequest
    ): Call<GetBarberServiceResponse>
}

