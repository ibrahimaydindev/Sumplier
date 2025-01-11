package com.sumplier.app.api

import android.util.Log
import com.sumplier.app.model.Ticket
import com.sumplier.app.interfaces.apiService.TicketApiService
import com.sumplier.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketApiManager {

    private val ticketApiService: TicketApiService = RetrofitClient.getClient().create(TicketApiService::class.java)
    fun getTicketAll(companyCode: String, onResult: (List<Ticket>?) -> Unit) {

        val call = ticketApiService.getTicketAll(companyCode)
        call.enqueue(object : Callback<List<Ticket>> {
            override fun onResponse(call: Call<List<Ticket>>, response: Response<List<Ticket>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

    fun getTicketByTicketCode(companyCode: String, ticketCode: String, onResult: (List<Ticket>?) -> Unit) {

        val call = ticketApiService.getTicketByTicketCode(companyCode, ticketCode)
        call.enqueue(object : Callback<List<Ticket>> {
            override fun onResponse(call: Call<List<Ticket>>, response: Response<List<Ticket>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

    fun getTicketByTicketDate(companyCode: String, startDate: String, endDate:String, ticketCode: String, onResult: (List<Ticket>?) -> Unit) {

        val call = ticketApiService.getTicketByDate(companyCode, startDate, endDate)
        call.enqueue(object : Callback<List<Ticket>> {
            override fun onResponse(call: Call<List<Ticket>>, response: Response<List<Ticket>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

    fun postTicket(ticket: Ticket, onResult: (Ticket?) -> Unit) {

        val call = ticketApiService.postTicket(ticket)
        call.enqueue(object : Callback<Ticket> {
            override fun onResponse(call: Call<Ticket>, response: Response<Ticket>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<Ticket>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }


}