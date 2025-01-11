package com.sumplier.app.api

import android.util.Log
import com.sumplier.app.model.CompanyDevice
import com.sumplier.app.interfaces.apiService.DeviceApiService
import com.sumplier.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeviceApiManager {
    private val deviceApiService: DeviceApiService = RetrofitClient.getClient().create(
        DeviceApiService::class.java)

    fun getCompanyLicences(companyCode: String, onResult: (List<CompanyDevice>?) -> Unit) {

        val call = deviceApiService.getCompanyDevices(companyCode)
        call.enqueue(object : Callback<List<CompanyDevice>> {
            override fun onResponse(call: Call<List<CompanyDevice>>, response: Response<List<CompanyDevice>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<CompanyDevice>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

}