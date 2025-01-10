package com.sumplier.app.interfaces.apiService

import com.sumplier.app.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("Users/GetUserLogin")
    fun getUserLogin(
        @Query("Email") email: String,
        @Query("Password") password: String,
        @Query("LoginType") loginType: Int
    ): Call<User>
}