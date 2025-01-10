package com.sumplier.app.interfaces.apiService

import com.sumplier.app.data.CompanyLicence
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LicenceApiService {
    @GET("CompanyLicence/GetCompanyLicence")
    fun getCompanyLicences(
        @Query("CompanyCode") companyCode: String,
    ): Call<List<CompanyLicence>>
}