package com.sumplier.app.data.model

data class TicketOrder(

    val id: Long,
    var ticketId: Int,
    val productCode: Long,
    val productName: String,
    var quantity: Double,
    var price: Double,
    var totalPrice: Double,
    var discountPrice: Double,
    val status: Int,
    val isChange: Boolean,
    val newQuantity: Double,
    val newPrice: Double?,
    val newTotalPrice: Double?,
    val companyCode: Long,
    val deviceCode: String,
)