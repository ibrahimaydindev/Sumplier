package com.sumplier.app.api

import android.util.Log
import com.sumplier.app.model.Category
import com.sumplier.app.interfaces.apiService.CategoryApiService
import com.sumplier.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryApiManager {
    private val categoryApiService: CategoryApiService = RetrofitClient.getClient().create(
        CategoryApiService::class.java)

    fun getCategory(companyCode: String, onResult: (List<Category>?) -> Unit) {

        val call = categoryApiService.getCategory(companyCode)
        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

}