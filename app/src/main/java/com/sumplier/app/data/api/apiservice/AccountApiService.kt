package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.CompanyAccount
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AccountApiService {
    @GET("CompanyAccount/GetCompanyAccountAll")
    suspend fun getCompanyAccountAll(
        @Query("CompanyCode") companyCode: String,
    ): Response<List<CompanyAccount>>

    @GET("CompanyAccount/GetCompanyAccountName")
    suspend fun getAccountByName(
        @Query("CompanyCode") companyCode: String,
        @Query("AccountName") accountName: String,
    ): Response<CompanyAccount>
}