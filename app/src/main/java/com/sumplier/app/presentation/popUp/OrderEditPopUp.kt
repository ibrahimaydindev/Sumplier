package com.sumplier.app.presentation.popUp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.sumplier.app.R
import com.sumplier.app.data.model.TicketOrder

class OrderEditPopup : DialogFragment() {
    private var order: TicketOrder? = null
    private var onOrderUpdated: ((TicketOrder) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_order_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val etQuantity = view.findViewById<EditText>(R.id.etQuantity)
        val productName = view.findViewById<TextView>(R.id.productName)
        val unitPrice = view.findViewById<TextView>(R.id.unitPrice)
        val btnConfirm = view.findViewById<ConstraintLayout>(R.id.btnConfirm)
        val btnCancel = view.findViewById<ConstraintLayout>(R.id.btnCancel)

        order?.let { currentOrder ->
            etQuantity.setText(currentOrder.quantity.toString())
            productName.text = currentOrder.productName
            unitPrice.text = "${currentOrder.totalPrice} TL"
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnConfirm.setOnClickListener {
            order?.let { currentOrder ->
                val newQuantity = etQuantity.text.toString().toDoubleOrNull() ?: currentOrder.quantity

                val updatedOrder = currentOrder.copy(
                    quantity = newQuantity,
                    totalPrice = newQuantity * currentOrder.price

                )

                onOrderUpdated?.invoke(updatedOrder)
                dismiss()
            }
        }
    }

    fun setOrder(ticketOrder: TicketOrder) {
        this.order = ticketOrder
    }

    fun setOnOrderUpdatedListener(listener: (TicketOrder) -> Unit) {
        this.onOrderUpdated = listener
    }
}