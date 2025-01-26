package com.sumplier.app.presentation.activity.popUp

import AccountAdapter
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.presentation.activity.listener.AccountSelectionListener


class AccountPopup(
    private val accounts: List<CompanyAccount>,
    private val listener: AccountSelectionListener
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.popup_account_selector, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvAccounts)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AccountAdapter(accounts) { selectedAccount ->
            listener.onAccountSelected(selectedAccount)
            dismiss()
        }

        return view
    }

    override fun onStart() {
        super.onStart()


        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT  // genişlik
            val height = ViewGroup.LayoutParams.MATCH_PARENT  // yükseklik
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener.onPopupDismissed()
    }
}
