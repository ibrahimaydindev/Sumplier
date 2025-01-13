package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.Ticket
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TicketApiService {
    @GET("Ticket/GetTicketAll")
    suspend fun getTicketAll(
        @Query("CompanyCode") companyCode: String
    ): Response<List<Ticket>>

    @GET("Ticket/GetTicketTicketCode")
    suspend fun getTicketByTicketCode(
        @Query("CompanyCode") companyCode: String,
        @Query("CompanyCode") ticketCode: String
    ): Response<List<Ticket>>

    @GET("Ticket/GetTicketDateTime")
    suspend fun getTicketByDate(
        @Query("CompanyCode") companyCode: String,
        @Query("StartDateTime") startDateTime: String,
        @Query("EndDateTime") endDateTime: String
    ): Response<List<Ticket>>

    @POST("Ticket/PostTicket")
    @Headers("Content-Type: application/json")
    suspend fun postTicket(
        @Body ticket: Ticket
    ): Response<Ticket>
}