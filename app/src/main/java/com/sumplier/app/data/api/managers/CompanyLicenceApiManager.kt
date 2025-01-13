package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.LicenceApiService
import com.sumplier.app.data.model.CompanyLicence

class CompanyLicenceApiManager (private val licenceApiService: LicenceApiService) {

    suspend fun getCompanyLicences(companyCode: String): List<CompanyLicence>? {
        return try {
            val response = licenceApiService.getCompanyLicences(companyCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e(
                    "CompanyLicenceApiManager",
                    "Error: ${response.code()} - ${response.message()}"
                )
                null
            }
        } catch (e: Exception) {
            Log.e("CompanyLicenceApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }
}
