package com.sumplier.app.model

data class TicketOrder(

    private val id: Int?,
    private val ticketCode: String?,
    private val productCode: String?,
    private val productName: String?,
    private val quantity: Double?,
    private val price: Double?,
    private val totalPrice: String?,
    private val status: Int?,
    private val isChange: Boolean?,
    private val newQuantity: Double?,
    private val newPrice: Double?,
    private val newTotalPrice: Double?,
    private val companyCode: String?,
)