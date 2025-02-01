package com.sumplier.app.presentation.activity.popUp

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.sumplier.app.R
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.presentation.activity.adapter.AccountAdapter
import com.sumplier.app.presentation.activity.listener.AccountSelectionListener

class AccountSelectionPopUp : DialogFragment() {

    private var accounts: List<CompanyAccount> = listOf()
    private var listener: AccountSelectionListener? = null
    private lateinit var adapter: AccountAdapter

    companion object {
        fun newInstance(accounts: List<CompanyAccount>, listener: AccountSelectionListener): AccountSelectionPopUp {
            val fragment = AccountSelectionPopUp()
            fragment.accounts = accounts
            fragment.listener = listener
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.account_list_layout)


        val editTextSearch = dialog.findViewById<EditText>(R.id.searchEditText)
        val listView = dialog.findViewById<ListView>(R.id.listView)

        val btnClose = dialog.findViewById<ImageButton>(R.id.btnClose)
        btnClose.setOnClickListener { dismiss() }

        adapter = AccountAdapter(requireContext(), accounts) { selectedAccount ->
            listener?.onAccountSelected(selectedAccount)
            dismiss()
        }

        listView.adapter = adapter

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener?.onPopupDismissed()
    }
}
