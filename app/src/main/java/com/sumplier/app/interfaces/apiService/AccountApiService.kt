package com.sumplier.app.interfaces.apiService

import com.sumplier.app.data.CompanyAccount
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountApiService {
    @GET("CompanyAccount/GetCompanyAccountAll")
    fun getCompanyAccountAll(
        @Query("CompanyCode") companyCode: String,
    ): Call<List<CompanyAccount>>

    @GET("CompanyAccount/GetCompanyAccountName")
    fun getAccountByName(
        @Query("CompanyCode") companyCode: String,
        @Query("AccountName") accountName: String,
    ): Call<CompanyAccount>
}