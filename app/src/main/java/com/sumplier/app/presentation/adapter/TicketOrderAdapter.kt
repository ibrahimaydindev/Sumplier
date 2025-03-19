package com.sumplier.app.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.data.model.TicketOrder

class TicketOrderAdapter(private val ticketOrders: List<TicketOrder>) :
    RecyclerView.Adapter<TicketOrderAdapter.TicketOrderViewHolder>() {

    inner class TicketOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val tvUnitPrice: TextView = itemView.findViewById(R.id.tvUnitPrice)
        private val tvTotalPrice: TextView = itemView.findViewById(R.id.tvTotalPrice)

        fun bind(ticketOrder: TicketOrder) {
            tvProductName.text = ticketOrder.productName
            tvQuantity.text = "${ticketOrder.quantity}"
            tvUnitPrice.text = "${ticketOrder.price}₺"
            tvTotalPrice.text = "${ticketOrder.totalPrice}₺"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_order_item, parent, false)
        return TicketOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketOrderViewHolder, position: Int) {
        holder.bind(ticketOrders[position])
    }

    override fun getItemCount() = ticketOrders.size
}