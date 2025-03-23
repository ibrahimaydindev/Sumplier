package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.Menu
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AccountApiService {
    @GET("CompanyAccount/GetCompanyAccountAll")
    fun getAccountsAll(
        @Query("CompanyCode") companyCode: Long,
        @Query("ResellerCode") resellerCode: Long,
    ): Call<List<CompanyAccount>>

}