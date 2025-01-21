package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.CompanyApiService
import com.sumplier.app.data.api.apiservice.UserApiService
import com.sumplier.app.data.api.retrofit.RetrofitClient
import com.sumplier.app.data.model.Company
import com.sumplier.app.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyApiManager {

    private val companyApiService: CompanyApiService = RetrofitClient.getClient().create(CompanyApiService::class.java)

    fun loginCompany(email: String, password: String, onResult: (Company?) -> Unit) {

        val call = this.companyApiService.getCompanyLogin(email, password)
        call.enqueue(object : Callback<Company> {
            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user?.id != 0) {
                        onResult(user)
                    } else {
                        onResult(null)
                    }
                } else {
                    Log.e("UserApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.e("UserApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

}

