package com.sumplier.app.interfaces

import com.sumplier.app.data.Ticket
import com.sumplier.app.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketApiService {
    @GET("Ticket/GetTicketAll")
    fun getTicketAll(
        @Query("CompanyCode") companyCode: String
    ): Call<List<Ticket>>
}