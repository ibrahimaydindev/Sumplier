package com.sumplier.app.data.enums

enum class TicketStatus(val statusCode: Int) {
    Waiting(0),
    InProgress(1),
    Accepted(2),
    Cancelled(3),
    OnTheWay(4),
    Delivered(5);

    fun getText(): String {
        return when (this) {
            Waiting -> "Waiting"
            InProgress -> "In Progress"
            Accepted -> "Accepted"
            Cancelled -> "Cancelled"
            OnTheWay -> "On The Way"
            Delivered -> "Delivered"
        }
    }

    companion object {
        private fun getStatus(code: Int): TicketStatus? {
            return entries.find { it.statusCode == code }
        }

        fun getStatusText(code: Int): String {
            val status = getStatus(code)
            return status?.getText() ?: "Unknown Status"
        }
    }
}