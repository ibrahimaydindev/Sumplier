package com.sumplier.app.data.model

data class Ticket(
    var id: Long,
    var ticketCode: Long,
    var companyCode: Long,
    var resellerCode: Long,
    var userCode: Long,
    var createDateTime: String,
    var modifiedDateTime: String,
    var total: Double,
    var taxTotal: Double,
    var generalTotal: Double,
    var paymentType: String? = null,
    var paymentStatus: Int,
    var description: String?,
    var status: Int,
    var deviceCode: String,
    var accountCode: Long,
    var accountName: String?,
    var ticketOrders: ArrayList<TicketOrder>
)




