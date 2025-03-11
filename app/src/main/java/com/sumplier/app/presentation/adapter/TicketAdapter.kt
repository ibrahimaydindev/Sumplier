package com.sumplier.app.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.enums.TimeFormat
import com.sumplier.app.data.helper.TimeHelper
import com.sumplier.app.data.model.CompanyAccount
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(filteredTickets[position])
    }

    override fun getItemCount() = filteredTickets.size

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTicketTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.tvTicketDate)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvTicketStatus)
        private val tvAccount: TextView = itemView.findViewById(R.id.tvTicketAccount)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(ticket: Ticket) {
            tvTitle.text = ticket.ticketCode.toString()
            tvStatus.text = ticket.status.toString()

            //set time
            val formattedDate = TimeHelper.formatDate(
                ticket.createDateTime,
                TimeFormat.ISO_WITHOUT_Z,
                TimeFormat.DISPLAY_FORMAT
            ) ?: "Tarih Yok" // if null

            tvDate.text = formattedDate

            //set account name
            val currentAccount : CompanyAccount? = Config.getInstance().getAccountById(ticket.accountCode)
            if (currentAccount != null) {
                tvAccount.text = currentAccount.accountName
            }

        }
    }
}