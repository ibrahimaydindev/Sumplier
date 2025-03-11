package com.sumplier.app.data.listener

import com.sumplier.app.data.model.TicketOrder

interface ConfirmationListener {
    fun onConfirmed(orders: List<TicketOrder>)

}