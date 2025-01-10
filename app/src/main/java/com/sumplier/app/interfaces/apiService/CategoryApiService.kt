package com.sumplier.app.interfaces.apiService

import com.sumplier.app.data.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApiService {
    @GET("Category/GetCategory")
    fun getCategory(
        @Query("CompanyCode") companyCode: String,
    ): Call<List<Category>>
}