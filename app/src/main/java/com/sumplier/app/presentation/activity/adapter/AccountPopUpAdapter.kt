import android.accounts.Account
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.data.model.CompanyAccount

class AccountAdapter(
    private val accounts: List<CompanyAccount>,
    private val onAccountClicked: (CompanyAccount) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    inner class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val accountNameTextView: TextView = view.findViewById(android.R.id.text1)

        fun bind(account: CompanyAccount) {
            accountNameTextView.text = account.accountName
            itemView.setOnClickListener { onAccountClicked(account) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(accounts[position])
    }

    override fun getItemCount(): Int = accounts.size
}
