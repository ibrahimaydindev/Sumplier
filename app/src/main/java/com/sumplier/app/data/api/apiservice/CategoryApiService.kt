package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApiService {
    @GET("Category/GetCategory")
    suspend fun getCategory(
        @Query("CompanyCode") companyCode: String,
    ): Response<List<Category>>
}