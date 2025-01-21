package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.Company
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CompanyApiService {
    @GET("Company/GetCompanyLogin")
    fun getCompanyLogin(
        @Query("Email") email: String,
        @Query("Password") password: String,

    ): Call<Company>
}