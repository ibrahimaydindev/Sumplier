package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.ProductApiService
import com.sumplier.app.data.model.Product

class ProductApiManager(private val productApiService: ProductApiService) {

    suspend fun getProductAll(companyCode: String): List<Product>? {
        return try {
            val response = productApiService.getProductAll(companyCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("ProductApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ProductApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }

    suspend fun getProductsByCategoryCode(
        companyCode: String,
        categoryCode: String
    ): List<Product>? {
        return try {
            val response = productApiService.getProductByCategoryCode(companyCode, categoryCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("ProductApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ProductApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }
}
