package com.sumplier.app.api

import android.util.Log
import com.sumplier.app.model.Product
import com.sumplier.app.interfaces.apiService.ProductApiService
import com.sumplier.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductApiManager {
    private val productApiService: ProductApiService = RetrofitClient.getClient().create(
        ProductApiService::class.java)

    fun getProductAll(companyCode: String, onResult: (List<Product>?) -> Unit) {

        val call = productApiService.getProductAll(companyCode)
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

    fun getProductsByCategoryCode(companyCode: String, categoryCode: String, onResult: (List<Product>?) -> Unit) {

        val call = productApiService.getProductByCategoryCode(companyCode, categoryCode)
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

}