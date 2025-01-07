package com.sumplier.app.interfaces

import com.sumplier.app.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("Users/GetUserLogin")
    fun getUserLogin(
        @Query("Email") email: String,
        @Query("Password") password: String,
        @Query("LoginType") loginType: Int
    ): Call<User>
}