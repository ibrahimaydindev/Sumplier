package com.sumplier.app.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.sumplier.app.R
import com.sumplier.app.data.model.CompanyAccount
import java.util.Locale

class AccountListAdapter(
    context: Context,
    private val accounts: List<CompanyAccount>,
    private val onAccountClick: (CompanyAccount) -> Unit
) : ArrayAdapter<CompanyAccount>(context, 0, accounts), Filterable {

    private var filteredAccounts: List<CompanyAccount> = accounts

    override fun getCount(): Int = filteredAccounts.size

    override fun getItem(position: Int): CompanyAccount = filteredAccounts[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.account_list_item, parent, false)

        val tvAccountName = view.findViewById<TextView>(R.id.tvAccountName)
        val tvPhoneNumber = view.findViewById<TextView>(R.id.tvPhoneNumber)

        val account = getItem(position)
        tvAccountName.text = account.accountName
        tvPhoneNumber.text = "+90 (531) 531 53 53"

        view.setOnClickListener { onAccountClick(account) }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""

                val filteredList = if (query.isEmpty()) {
                    accounts
                } else {
                    accounts.filter { it.accountName.lowercase(Locale.getDefault()).contains(query) }
                }

                return FilterResults().apply { values = filteredList; count = filteredList.size }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredAccounts = results?.values as? List<CompanyAccount> ?: accounts
                notifyDataSetChanged()
            }
        }
    }
}
