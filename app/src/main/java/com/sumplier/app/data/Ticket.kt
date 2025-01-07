package com.sumplier.app.data

data class Ticket(
    private val id: Int?,
    private val ticketCode: String?,
    private val companyCode: String?,
    private val userCode: String?,
    private val createDateTime: String?,
    private val modifiedDateTime: String?,
    private val total: Double?,
    private val taxTotal: Double?,
    private val generalTotal: Double?,
    private val paymentType: String?,
    private val description: String?,
    private val status: Int?,
)
