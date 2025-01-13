package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.AccountApiService
import com.sumplier.app.data.model.CompanyAccount


class AccountApiManager(private val accountApiService: AccountApiService) {


    suspend fun getAccountAll(companyCode: String): List<CompanyAccount>? {
        return try {
            val response = accountApiService.getCompanyAccountAll(companyCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("AccountApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AccountApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }

    suspend fun getAccountByName(companyCode: String, accountName: String): CompanyAccount? {
        return try {
            val response = accountApiService.getAccountByName(companyCode, accountName)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("AccountApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AccountApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }
}
