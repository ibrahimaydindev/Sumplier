package com.sumplier.app.data.model

data class TicketOrder(

    val id: Long,
    var ticketCode: Long,
    val productCode: Long,
    val productName: String,
    val quantity: Double,
    val price: Double,
    val totalPrice: Double,
    val status: Int,
    val isChange: Boolean,
    val newQuantity: Double,
    val newPrice: Double?,
    val newTotalPrice: Double?,
    val companyCode: Long,
    val deviceCode: String,
)