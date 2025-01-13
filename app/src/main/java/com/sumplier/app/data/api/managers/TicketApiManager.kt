package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.TicketApiService
import com.sumplier.app.data.model.Ticket

class TicketApiManager(private val ticketApiService: TicketApiService) {

    suspend fun getTicketAll(companyCode: String): List<Ticket>? {
        return try {
            val response = ticketApiService.getTicketAll(companyCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("TicketApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("TicketApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }

    suspend fun getTicketByTicketCode(companyCode: String, ticketCode: String): List<Ticket>? {
        return try {
            val response = ticketApiService.getTicketByTicketCode(companyCode, ticketCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("TicketApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("TicketApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }

    suspend fun getTicketByTicketDate(companyCode: String, startDate: String, endDate: String): List<Ticket>? {
        return try {
            val response = ticketApiService.getTicketByDate(companyCode, startDate, endDate)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("TicketApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("TicketApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }

    suspend fun postTicket(ticket: Ticket): Ticket? {
        return try {
            val response = ticketApiService.postTicket(ticket)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("TicketApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("TicketApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }
}
