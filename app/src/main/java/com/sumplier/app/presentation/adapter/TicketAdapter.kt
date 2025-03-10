package com.sumplier.app.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.data.model.Ticket

class TicketAdapter : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    private var tickets = mutableListOf<Ticket>()
    private var filteredTickets = mutableListOf<Ticket>()

    fun setTickets(newTickets: List<Ticket>) {
        tickets = newTickets.toMutableList()
        filteredTickets = tickets.toMutableList()
        notifyDataSetChanged()
    }

    fun filterTickets(query: String) {
        filteredTickets = if (query.isEmpty()) {
            tickets.toMutableList()
        } else {
            tickets.filter { ticket ->
                ticket.ticketCode.toString().contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_item, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(filteredTickets[position])
    }

    override fun getItemCount() = filteredTickets.size

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTicketTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.tvTicketDate)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvTicketStatus)

        fun bind(ticket: Ticket) {
            tvTitle.text = ticket.ticketCode.toString()
            tvDate.text = ticket.createDateTime
            tvStatus.text = ticket.status.toString()
        }
    }
}