package com.sumplier.app.presentation.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.data.model.CompanyAccount

class AccountAdapter(
    private var accounts: List<CompanyAccount>,
    private val onEditClick: (CompanyAccount) -> Unit,
    private val onCashClick: (CompanyAccount) -> Unit,
    private val onItemClick: (CompanyAccount) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private var filteredAccounts = accounts

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
        val editImageView: ImageView = itemView.findViewById(R.id.editImageView)
        val cashImageView: ImageView = itemView.findViewById(R.id.cashImageView)

        fun bind(account: CompanyAccount) {
            nameTextView.text = account.accountName
            //holder.phoneTextView.text = account.companyCode.toString()

            editImageView.setOnClickListener { onEditClick(account) }
            cashImageView.setOnClickListener { onCashClick(account) }

            itemView.setOnClickListener {
                onItemClick(account)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = filteredAccounts[position]
        holder.bind(account)
    }

    override fun getItemCount() = filteredAccounts.size

    fun filter(query: String) {
        filteredAccounts = if (query.isEmpty()) {
            accounts
        } else {
            accounts.filter { it.accountName.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    fun updateAccounts(newAccounts: List<CompanyAccount>) {
        accounts = newAccounts
        filteredAccounts = newAccounts
        notifyDataSetChanged()
    }
}