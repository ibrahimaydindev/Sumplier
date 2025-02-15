import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.data.model.TicketOrder

class BasketDetailAdapter(
    private var items: ArrayList<TicketOrder>,
    private val onItemRemove: (Int) -> Unit) : RecyclerView.Adapter<BasketDetailAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.tvProductName)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val quantity: TextView = view.findViewById(R.id.tvQuantity)
        val btnRemove: ImageButton = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.basket_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.productName.text = item.productName
        holder.price.text = "${item.price} TL"
        holder.quantity.text = "x${item.quantity}"

        holder.btnRemove.setOnClickListener {
                //items.removeAt(position)
                onItemRemove(position)
                notifyDataSetChanged()
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: ArrayList<TicketOrder>) {
        items = newItems
        notifyDataSetChanged()
    }
}