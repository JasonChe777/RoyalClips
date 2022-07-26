package com.example.royalclips.model

import com.example.royalclips.model.Constants.LOGIN_END_POINT
import com.example.royalclips.model.Constants.REGISTER_END_POINT
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @Headers("Content-type:application/json")
    @POST(LOGIN_END_POINT)
    fun doLogin(
        @Field("mobileNo") mobileNo: String,
        @Field("password") password: String
    ): Call<LoginResponse>



    @Headers("Content-type:application/json")
    @POST(REGISTER_END_POINT)
    fun doRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>
}

