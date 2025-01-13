package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.CompanyDevice
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DeviceApiService {
    @GET("CompanyDevice/GetCompanyDevice")
    suspend fun getCompanyDevices(
        @Query("CompanyCode") companyCode: String,
    ): Response<List<CompanyDevice>>
}