package com.sumplier.app.data.model

data class TicketOrder(

    val id: Int?,
    val ticketCode: String?,
    val productCode: String?,
    val productName: String?,
    val quantity: Double?,
    val price: Double?,
    val totalPrice: String?,
    val status: Int?,
    val isChange: Boolean?,
    val newQuantity: Double?,
    val newPrice: Double?,
    val newTotalPrice: Double?,
    val companyCode: String?,
)