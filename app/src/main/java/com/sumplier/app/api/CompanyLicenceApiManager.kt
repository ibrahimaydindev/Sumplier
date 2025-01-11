package com.sumplier.app.api

import android.util.Log
import com.sumplier.app.model.CompanyLicence
import com.sumplier.app.interfaces.apiService.LicenceApiService
import com.sumplier.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyLicenceApiManager {
    private val licenceApiService: LicenceApiService = RetrofitClient.getClient().create(
        LicenceApiService::class.java)

    fun getCompanyLicences(companyCode: String, onResult: (List<CompanyLicence>?) -> Unit) {

        val call = licenceApiService.getCompanyLicences(companyCode)
        call.enqueue(object : Callback<List<CompanyLicence>> {
            override fun onResponse(call: Call<List<CompanyLicence>>, response: Response<List<CompanyLicence>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<CompanyLicence>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

}