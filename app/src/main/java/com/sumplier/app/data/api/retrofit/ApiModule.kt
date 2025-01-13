package com.sumplier.app.data.api.retrofit

import com.sumplier.app.data.api.apiservice.AccountApiService
import com.sumplier.app.data.api.apiservice.CategoryApiService
import com.sumplier.app.data.api.apiservice.DeviceApiService
import com.sumplier.app.data.api.apiservice.LicenceApiService
import com.sumplier.app.data.api.apiservice.ProductApiService
import com.sumplier.app.data.api.apiservice.TicketApiService
import com.sumplier.app.data.api.apiservice.UserApiService
import com.sumplier.app.data.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiModule {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getClient(): Retrofit = retrofit

    val userApiService: UserApiService = getClient().create(UserApiService::class.java)
    val accountApiService: AccountApiService = getClient().create(AccountApiService::class.java)
    val categoryApiService: CategoryApiService = getClient().create(CategoryApiService::class.java)
    val productApiService: ProductApiService = getClient().create(ProductApiService::class.java)
    val deviceApiService: DeviceApiService = getClient().create(DeviceApiService::class.java)
    val ticketApiService: TicketApiService = getClient().create(TicketApiService::class.java)
    val companyLicence: LicenceApiService = getClient().create(LicenceApiService::class.java)

}