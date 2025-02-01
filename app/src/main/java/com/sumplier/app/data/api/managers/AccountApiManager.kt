package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.AccountApiService
import com.sumplier.app.data.api.apiservice.MenuApiService
import com.sumplier.app.data.api.retrofit.RetrofitClient
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.Menu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AccountApiManager {

    private val accountApiService: AccountApiService = RetrofitClient.getClient().create(AccountApiService::class.java)

    fun getAccounts(companyCode: Long, resellerCode: Long, onResult: (List<CompanyAccount>?) -> Unit) {

        val call = accountApiService.getAccountsAll(companyCode, resellerCode)
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

}
