package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.Menu
import com.sumplier.app.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MenuApiService {
    @GET("Menu/GetMenu")
    fun getMenus(
        @Query("CompanyCode") companyCode: Long,
        @Query("ResellerCode") resellerCode: Long,
    ): Call<List<Menu>>
}