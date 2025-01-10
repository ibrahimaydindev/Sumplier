package com.sumplier.app.interfaces.apiService

import com.sumplier.app.data.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
    @GET("Product/GetProductAll")
    fun getProductAll(
        @Query("CompanyCode") companyCode: String,
    ): Call<List<Product>>

    @GET("Product/GetProductCategoryCode")
    fun getProductByCategoryCode(
        @Query("CompanyCode") companyCode: String,
        @Query("CategoryCode") categoryCode: String,
    ): Call<List<Product>>
}