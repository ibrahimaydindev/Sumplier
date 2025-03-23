package com.sumplier.app.presentation.popUp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.data.enums.DateFormat
import com.sumplier.app.data.enums.TicketStatus
import com.sumplier.app.data.helper.TimeHelper
import com.sumplier.app.data.model.Ticket
import com.sumplier.app.presentation.adapter.TicketOrderAdapter

class TicketDetailPopup : DialogFragment() {
    private var ticket: Ticket? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.popup_ticket_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticket?.let { ticket ->
            view.apply {
                findViewById<TextView>(R.id.tv_ticket_account).text = ticket.accountName.toString()
                findViewById<TextView>(R.id.tvDateTime).text = TimeHelper.convertFormat(ticket.createDateTime, DateFormat.CLOUD_FORMAT, DateFormat.DDMMYYYY_HHMM)
                findViewById<TextView>(R.id.tvStatus).text = TicketStatus.getStatusText(ticket.status)

                // List ticketOrders
                val rvOrders = findViewById<RecyclerView>(R.id.rvTicketOrders)
                rvOrders.layoutManager = LinearLayoutManager(context)
                rvOrders.adapter = TicketOrderAdapter(ticket.ticketOrders)
            }
        }

        //Close button
        view.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        fun newInstance(ticket: Ticket): TicketDetailPopup {
            return TicketDetailPopup().apply {
                this.ticket = ticket
            }
        }
    }
}