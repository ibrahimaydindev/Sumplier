package com.sumplier.app.model

data class Ticket(
    var id: Int? = null,
    var ticketCode: String? = null,
    var companyCode: String? = null,
    var userCode: String? = null,
    var createDateTime: String? = null,
    var modifiedDateTime: String? = null,
    var total: Double? = null,
    var taxTotal: Double? = null,
    var generalTotal: Double? = null,
    var paymentType: String? = null,
    var description: String? = null,
    var status: Int? = null,
    var deviceCode: String? = null
)


