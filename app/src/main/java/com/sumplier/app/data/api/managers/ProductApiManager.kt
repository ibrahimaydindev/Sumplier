package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.CategoryApiService
import com.sumplier.app.data.api.apiservice.ProductApiService
import com.sumplier.app.data.api.retrofit.RetrofitClient
import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductApiManager {

    private val productApiService: ProductApiService = RetrofitClient.getClient().create(ProductApiService::class.java)

    fun getProductsAll(companyCode: Long, resellerCode: Long, onResult: (List<Product>?) -> Unit) {

        val call = productApiService.getProductAll(companyCode, resellerCode)
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
