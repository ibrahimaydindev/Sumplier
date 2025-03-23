package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
    @GET("Product/GetProductAll")
    fun getProductAll(
        @Query("CompanyCode") companyCode: Long,
        @Query("ResellerCode") resellerCode: Long,
    ): Call<List<Product>>

    @GET("Product/GetProductCategoryCode")
    fun getProductByCategoryCode(
        @Query("CompanyCode") companyCode: String,
        @Query("CategoryCode") categoryCode: String,
    ): Response<List<Product>>
}