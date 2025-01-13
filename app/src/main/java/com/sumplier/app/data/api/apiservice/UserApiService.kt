package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @GET("Users/GetUserLogin")
    suspend fun getUserLogin(
        @Query("Email") email: String,
        @Query("Password") password: String,
        @Query("LoginType") loginType: Int
    ): Response<User>

    @POST("users/PostUsers")
    suspend fun postUserLogin(@Body request: User): Response<User>
}