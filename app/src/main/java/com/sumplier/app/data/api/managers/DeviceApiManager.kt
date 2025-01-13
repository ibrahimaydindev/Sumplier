package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.DeviceApiService
import com.sumplier.app.data.model.CompanyDevice

class DeviceApiManager(private val deviceApiService: DeviceApiService) {

    suspend fun getCompanyDevices(companyCode: String): List<CompanyDevice>? {
        return try {
            val response = deviceApiService.getCompanyDevices(companyCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("DeviceApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("DeviceApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }
}
