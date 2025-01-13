package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.CategoryApiService
import com.sumplier.app.data.model.Category

class CategoryApiManager(private val categoryApiService: CategoryApiService) {

    suspend fun getCategory(companyCode: String): List<Category>? {
        return try {
            val response = categoryApiService.getCategory(companyCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("CategoryApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("CategoryApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }
}
