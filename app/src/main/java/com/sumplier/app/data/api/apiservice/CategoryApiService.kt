package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.Menu
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApiService {
    @GET("Category/GetCategory")
    fun getCategories(
        @Query("CompanyCode") companyCode: String?,
        @Query("ResellerCode") resellerCode: String?,
    ): Call<List<Category>>
}