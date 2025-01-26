package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.CategoryApiService
import com.sumplier.app.data.api.retrofit.RetrofitClient
import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.Menu
import com.sumplier.app.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryApiManager {

    private val categoryApiService: CategoryApiService = RetrofitClient.getClient().create(CategoryApiService::class.java)

    fun getCategories(companyCode: String?, resellerCode: String?, onResult: (List<Category>?) -> Unit) {

        val call = categoryApiService.getCategories(companyCode, resellerCode)
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
