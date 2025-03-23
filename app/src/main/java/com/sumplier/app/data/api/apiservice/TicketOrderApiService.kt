package com.sumplier.app.data.api.apiservice

import com.sumplier.app.data.model.Ticket
import com.sumplier.app.data.model.TicketOrder
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TicketOrderApiService {
    @POST("TicketOrder/PostTicketOrder")
    @Headers("Content-Type: application/json")
    fun postTicketOrder(
        @Body ticket: TicketOrder
    ): Call<TicketOrder>
}