package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.TicketApiService
import com.sumplier.app.data.api.retrofit.RetrofitClient
import com.sumplier.app.data.model.Ticket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.sumplier.app.data.model.Product

class TicketApiManager {
    private val ticketApiService: TicketApiService = RetrofitClient.getClient().create(TicketApiService::class.java)

    fun postTicket1(ticket: Ticket, onResult: (Long?) -> Unit) {

        Log.d("TicketApiManager", "Sending Ticket: ${Gson().toJson(ticket)}")

        val call = ticketApiService.postTicket1(ticket)
        call.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                if (response.isSuccessful) {
                    val ticketCode = response.body()
                    if (ticketCode != null) {
                        onResult(ticketCode)
                    } else {
                        onResult(null)
                    }
                } else {
                    Log.e("TicketApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                Log.e("TicketApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

    fun postTicket(ticket: Ticket, onResult: (Boolean) -> Unit) {

        Log.d("TicketApiManager", "Sending Ticket: ${Gson().toJson(ticket)}")

        val call = ticketApiService.postTicket(ticket)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("TicketApiManager", "Ticket posted successfully!")
                    onResult(true)
                } else {
                    Log.e("TicketApiManager", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("TicketApiManager", "Failure: ${t.message}")
                onResult(false)
            }
        })
    }

    fun getTicketAll(companyCode: Long, resellerCode: Long, onResult: (List<Ticket>?) -> Unit) {

        val call = ticketApiService.getTicketAll(companyCode, resellerCode)
        call.enqueue(object : Callback<List<Ticket>> {
            override fun onResponse(call:Call<List<Ticket>>, response: Response<List<Ticket>>) {
                if (response.isSuccessful) {
                    val ticket1 = response.body()
                    if (ticket1 != null) {
                        onResult(ticket1)
                    } else {
                        onResult(null)
                    }
                } else {
                    Log.e("TicketApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                Log.e("TicketApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }
}
