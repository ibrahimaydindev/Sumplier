package com.sumplier.app.interfaces.apiService

import com.sumplier.app.data.Ticket
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketApiService {
    @GET("Ticket/GetTicketAll")
    fun getTicketAll(
        @Query("CompanyCode") companyCode: String
    ): Call<List<Ticket>>

    @GET("Ticket/GetTicketTicketCode")
    fun getTicketByTicketCode(
        @Query("CompanyCode") companyCode: String,
        @Query("CompanyCode") ticketCode: String
    ): Call<List<Ticket>>

    @GET("Ticket/GetTicketDateTime")
    fun getTicketByDate(
        @Query("CompanyCode") companyCode: String,
        @Query("StartDateTime") startDateTime: String,
        @Query("EndDateTime") endDateTime: String
    ): Call<List<Ticket>>
}