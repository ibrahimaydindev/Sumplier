package com.sumplier.app.presentation.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.enums.DateFormat
import com.sumplier.app.data.enums.TicketStatus
import com.sumplier.app.data.helper.TimeHelper
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.Ticket
import com.sumplier.app.presentation.popUp.TicketDetailPopup

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

    inner class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTicketAccount: TextView = itemView.findViewById(R.id.tvTicketAccount)
        private val tvDate: TextView = itemView.findViewById(R.id.tvTicketDate)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvTicketStatus)
        private val tvAccountPhone: TextView = itemView.findViewById(R.id.tvAccountPhone)
        private val tvPrice:  TextView = itemView.findViewById(R.id.tvTicketPrice)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val ticket = filteredTickets[position]
                    showTicketDetailPopup(ticket, itemView.context)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(ticket: Ticket) {
            tvTicketAccount.text = ticket.accountName
            tvStatus.text = TicketStatus.getStatusText(ticket.status)
            tvPrice.text = ticket.generalTotal.toString() + "₺"

            //set time
            val formattedDate = TimeHelper.convertFormat(
                ticket.createDateTime,
                DateFormat.CLOUD_FORMAT,
                DateFormat.DDMMYYYY_HHMM
            ) ?: "Tarih Yok" // if null

            tvDate.text = formattedDate

            //set account phone...
            val currentAccount : CompanyAccount? = Config.getInstance().getAccountByCode(ticket.accountCode)
            if (currentAccount != null) {
                tvAccountPhone.text = currentAccount.phoneNumber
            }
        }
    }

    private fun showTicketDetailPopup(ticket: Ticket, context: Context) {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        TicketDetailPopup.newInstance(ticket)
            .show(fragmentManager, "TicketDetailPopup")
    }
}