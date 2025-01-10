package com.sumplier.app.api

import android.util.Log
import com.sumplier.app.data.CompanyAccount
import com.sumplier.app.interfaces.apiService.AccountApiService
import com.sumplier.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountApiManager {
    private val accountApiService: AccountApiService = RetrofitClient.getClient().create(
        AccountApiService::class.java)

    fun getAccountAll(companyCode: String, onResult: (List<CompanyAccount>?) -> Unit) {

        val call = accountApiService.getCompanyAccountAll(companyCode)
        call.enqueue(object : Callback<List<CompanyAccount>> {
            override fun onResponse(call: Call<List<CompanyAccount>>, response: Response<List<CompanyAccount>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<CompanyAccount>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

    fun getAccountByName(companyCode: String, accountName: String, onResult: (CompanyAccount?) -> Unit) {

        val call = accountApiService.getAccountByName(companyCode, accountName)
        call.enqueue(object : Callback<CompanyAccount> {
            override fun onResponse(call: Call<CompanyAccount>, response: Response<CompanyAccount>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<CompanyAccount>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

}