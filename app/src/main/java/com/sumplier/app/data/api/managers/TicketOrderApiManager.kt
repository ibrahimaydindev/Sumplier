package com.sumplier.app.data.api.managers

import android.util.Log
import com.google.gson.Gson
import com.sumplier.app.data.api.apiservice.TicketOrderApiService
import com.sumplier.app.data.api.retrofit.RetrofitClient
import com.sumplier.app.data.model.TicketOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketOrderApiManager {
    private val ticketOrderApiService: TicketOrderApiService = RetrofitClient.getClient().create(TicketOrderApiService::class.java)

    fun postTicketOrder(ticketOrder: TicketOrder, onResult: (Boolean) -> Unit) {

        Log.d("TicketOrderApiManager", "Sending TicketOrder: ${Gson().toJson(ticketOrder)}")

        val call = ticketOrderApiService.postTicketOrder(ticketOrder)
        call.enqueue(object : Callback<TicketOrder> {
            override fun onResponse(call: Call<TicketOrder>, response: Response<TicketOrder>) {
                if (response.code() == 201) {
                    onResult(true)
                } else {
                    Log.e("UserApiManager", "Error: ${response.code()}")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<TicketOrder>, t: Throwable) {
                Log.e("UserApiManager", "Failure: ${t.message}")
                onResult(false)
            }
        })
    }

}
