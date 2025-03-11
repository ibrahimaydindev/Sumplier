package com.sumplier.app.data.model

data class Ticket(
    var ticketCode: Long?,
    var companyCode: Long?,
    var resellerCode: Long?,
    var userCode: Long?,
    var createDateTime: String? = null,
    var modifiedDateTime: String? = null,
    var total: Double? = null,
    var taxTotal: Double? = null,
    var generalTotal: Double? = null,
    var paymentType: String? = null,
    var description: String? = null,
    var status: Int? = null,
    var deviceCode: String? = null,
    var statusName: String? = null,
    var accountCode: Long? ,
    var ticketOrders: ArrayList<TicketOrder>
)




