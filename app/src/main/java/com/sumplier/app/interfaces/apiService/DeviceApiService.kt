package com.sumplier.app.interfaces.apiService

import com.sumplier.app.model.CompanyDevice
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DeviceApiService {
    @GET("CompanyDevice/GetCompanyDevice")
    fun getCompanyDevices(
        @Query("CompanyCode") companyCode: String,
    ): Call<List<CompanyDevice>>
}