package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.CompanyLicence
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LicenceApiService {
    @GET("CompanyLicence/GetCompanyLicence")
    suspend fun getCompanyLicences(
        @Query("CompanyCode") companyCode: String,
    ): Response<List<CompanyLicence>>
}