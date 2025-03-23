package com.sumplier.app.presentation.popUp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sumplier.app.databinding.QuantityPopupBinding

class QuantityPopup : DialogFragment() {
    private var onQuantitySelected: ((Double) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)



        val binding = QuantityPopupBinding.inflate(inflater, container, false)

        binding.apply {
            quantityEditText.setText("1.0")

            confirmButton.setOnClickListener {
                val enteredQuantity = quantityEditText.text.toString().toDoubleOrNull() ?: 1.0
                onQuantitySelected?.invoke(enteredQuantity)
                dismiss()
            }

            cancelButton.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    fun setOnQuantitySelectedListener(listener: (Double) -> Unit) {
        onQuantitySelected = listener
    }
}