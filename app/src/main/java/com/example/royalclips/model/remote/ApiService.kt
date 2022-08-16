package com.example.royalclips.model.remote


import com.example.royalclips.model.Constants.LOGIN_END_POINT
import com.example.royalclips.model.Constants.REGISTER_END_POINT
import com.example.royalclips.model.data.BaseResponse
import com.example.royalclips.model.data.bookAppointments.BookResponse
import com.example.royalclips.model.data.contacts.ContactsResponse
import com.example.royalclips.model.data.currentAppointments.CurrentAppointmentsResponse
import com.example.royalclips.model.data.dashboard.DashboardResponse
import com.example.royalclips.model.data.getAppointments.GetAppointmentsResponse
import com.example.royalclips.model.data.getBarber.GetBarberResponse
import com.example.royalclips.model.data.getBarberServices.GetBarberServiceResponse
import com.example.royalclips.model.data.getPhoneVerificationCode.GetPhoneVerificationCodeResponse
import com.example.royalclips.model.data.getServiceCategory.GetServiceCategoryResponse
import com.example.royalclips.model.data.getServicesByCategory.GetServicesByCategoryResponse
import com.example.royalclips.model.data.login.LoginRequest
import com.example.royalclips.model.data.login.LoginResponse
import com.example.royalclips.model.data.register.RegisterRequest
import com.example.royalclips.model.data.register.RegisterResponse
import com.example.royalclips.model.data.workingHour.WorkingHourResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Headers("Content-type:application/json")
    @POST(LOGIN_END_POINT)
    fun doLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @Headers("Content-type: application/json")
    @POST("appUser/updateFcmToken")
    fun updateFcmToken(
        @Header("ps_auth_token") ps_auth_token: String,
        @Body updateReq: RequestBody
    ): Single<BaseResponse>

    @Headers("Content-type: application/json")
    @POST("appUser/logout")
    fun logout(
        @Header("ps_auth_token") ps_auth_token: String,
        @Body logoutReq: RequestBody
    ): Single<BaseResponse>

    @Headers("Content-type:application/json")
    @POST(REGISTER_END_POINT)
    fun doRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @Headers("Content-type: application/json")
    @GET("appUser/dashboard")
    fun dashboard(): Call<DashboardResponse>

    @Headers("Content-type:application/json")
    @GET("barber/getBarbers")
    fun getBarbers(): Call<GetBarberResponse>

    @Headers("Content-type:application/json")
    @POST("barber/getBarberServices1")
    fun getBarberServices(
        @Body getBarberServicesReq: RequestBody
    ): Call<GetBarberServiceResponse>

    @Headers("Content-type: application/json")
    @GET
    fun currentAppointments(
        @Url url: String
    ): Call<CurrentAppointmentsResponse>

    @Headers("Content-type: application/json")
    @POST("/appointment/book")
    fun bookAppointment(
        @Header("ps_auth_token") ps_auth_token: String,
        @Body bookReq: RequestBody
    ): Call<BookResponse>

    @Headers("Content-type: application/json")
    @POST("/appointment/reschedule")
    fun rescheduleAppointment(
        @Header("ps_auth_token") ps_auth_token: String,
        @Body bookReq: RequestBody
    ): Call<BookResponse>

    @Headers("Content-type: application/json")
    @GET
    fun cancelAppointment(
        @Url url: String,
        @Header("ps_auth_token") ps_auth_token: String
    ): Call<BookResponse>

    @Headers("Content-type: application/json")
    @GET
    fun getAppointments(
        @Url url: String,
        @Header("ps_auth_token") ps_auth_token: String
    ): Call<GetAppointmentsResponse>

    @Headers("Content-type: application/json")
    @GET
    fun getAppointmentDetail(
        @Url url: String,
        @Header("ps_auth_token") ps_auth_token: String
    ): Call<BookResponse>

    @Headers("Content-type: application/json")
    @GET("service/getServiceCategory")
    fun getServiceCategory(): Call<GetServiceCategoryResponse>

    @Headers("Content-type: application/json")
    @GET
    fun getServicesByCategory(@Url url: String): Call<GetServicesByCategoryResponse>

    @Headers("Content-type: application/json")
    @GET("workingHours/getList")
    fun getWorkingHours(): Single<WorkingHourResponse>

    @Headers("Content-type: application/json")
    @GET("shopContacts/getList")
    suspend fun getContacts(): Response<ContactsResponse>

    @Headers("Content-type: application/json")
    @GET
    suspend fun getPhoneVerificationCode (@Url url: String): GetPhoneVerificationCodeResponse

    @Headers("Content-type: application/json")
    @POST("appUser/resetPassword")
    suspend fun resetPassword (@Body updateReq: RequestBody): BaseResponse
}

