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
    fun postTicket(ticket: Ticket, onResult: (Long) -> Unit) {

        Log.d("TicketApiManager", "Sending Ticket: ${Gson().toJson(ticket)}")

        val call = ticketApiService.postTicket(ticket)
        call.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {

                if (response.isSuccessful) {
                    val ticketId = response.body() ?: -1L
                    Log.d("TicketApiManager | postTicket", "Ticket posted successfully! ID: $ticketId")
                    onResult(ticketId)
                } else {
                    Log.e("TicketApiManager | postTicket", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                    onResult(-1L)
                }
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                Log.e("TicketApiManager | postTicket", "Failure: ${t.message}")
                onResult(-1L)
            }
        })
    }

    fun getTicketAll(companyCode: Long, resellerCode: Long, startDateTime:String, endDateTime:String, onResult: (List<Ticket>?) -> Unit) {

        val call = ticketApiService.getTicketAll(companyCode, resellerCode, startDateTime, endDateTime)
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
                    Log.e("TicketApiManager | getTicketAll", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                Log.e("TicketApiManager | getTicketAll", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

    fun getTicketByUserCode(companyCode: Long, resellerCode: Long, startDateTime:String, endDateTime:String, userCode:Long, onResult: (List<Ticket>?) -> Unit) {

        val call = ticketApiService.getTicketByUserCode(companyCode, resellerCode, startDateTime, endDateTime, userCode)
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
                    Log.e("TicketApiManager | getTicketByUserCode", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                Log.e("TicketApiManager | getTicketByUserCode", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }
}
