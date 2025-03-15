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
        fun getStatus(code: Int): TicketStatus? {
            return values().find { it.statusCode == code }
        }

        // Status enum'ına karşılık gelen metni döndüren fonksiyon
        fun getStatusText(code: Int): String {
            val status = getStatus(code)
            return status?.getText() ?: "Unknown Status"
        }
    }
}