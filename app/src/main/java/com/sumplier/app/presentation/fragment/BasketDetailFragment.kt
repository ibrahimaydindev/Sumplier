import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.data.listener.ConfirmationListener
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.TicketOrder
import com.sumplier.app.presentation.activity.MainActivity
import com.sumplier.app.presentation.popUp.OrderEditPopup

class BasketDetailFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var basketDetailAdapter: BasketDetailAdapter
    private var basketItems: ArrayList<TicketOrder> = ArrayList()
    private var onBasketUpdated: ((ArrayList<TicketOrder>) -> Unit)? = null
    private lateinit var currentAccount: CompanyAccount

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_basket_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        recyclerView = view.findViewById(R.id.basketDetailRecyclerView)
        setupRecyclerView()
        
        // Geri dönüş butonu
        view.findViewById<ImageView>(R.id.btnBack)?.setOnClickListener {
            onBasketUpdated?.invoke(basketItems)
            requireActivity().findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.GONE
            parentFragmentManager.popBackStack()
        }

        // Clear items butonu
        view.findViewById<ImageView>(R.id.btnClearItems)?.setOnClickListener {

            val warningPopup = WarningPopup(
                warningText = "Tüm sipariş kalemlerini silmek istediğinize emin misiniz?",
                warningIcon = R.drawable.question_mark,
                yesButtonText = "Evet",
                noButtonText = "Hayır",
                showNoButton = true
            ).apply {
                setOnYesClickListener {
                    basketItems.clear()
                    onBasketUpdated?.invoke(basketItems)
                    requireActivity().findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.GONE
                    parentFragmentManager.popBackStack()
                }
                setOnNoClickListener {
                    dismiss()
                }
            }
            warningPopup.show(parentFragmentManager, "WarningPopup")
        }

        //Confirm Button
        view.findViewById<ConstraintLayout>(R.id.confirm_button)?.setOnClickListener {
            val confirmationPopup = ConfirmationPopup().apply {
                setOrderList(basketItems)
                setCurrentAccount(currentAccount)
                setConfirmationListener(object : ConfirmationListener {
                    override fun onConfirmed(orders: List<TicketOrder>) {
                        showSuccess {
                            basketItems.clear()
                            onBasketUpdated?.invoke(basketItems)

                            openMainActivity()
                        }
                    }
                })
            }
            confirmationPopup.show(parentFragmentManager, "ConfirmationPopup")
        }
    }
    private fun openMainActivity(){

        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    fun setBasketItems(items: ArrayList<TicketOrder>) {
        basketItems = items
        if (::basketDetailAdapter.isInitialized) {
            basketDetailAdapter.updateItems(items)
        }
    }

    fun setCurrentAccount (account : CompanyAccount){
        currentAccount = account
    }

    fun setOnBasketUpdatedListener(listener: (ArrayList<TicketOrder>) -> Unit) {
        onBasketUpdated = listener
    }

    private fun setupRecyclerView() {
        basketDetailAdapter = BasketDetailAdapter(
            basketItems,
            { position ->
                basketItems.removeAt(position)
                basketDetailAdapter.notifyItemRemoved(position)

                if (basketItems.isEmpty()) {
                    onBasketUpdated?.invoke(basketItems)
                    requireActivity().findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.GONE
                    parentFragmentManager.popBackStack()
                }
            },
            { order ->
                showOrderEditPopup(order)
            }
        )
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = basketDetailAdapter
        }
    }

    private fun showOrderEditPopup(order: TicketOrder) {
        OrderEditPopup().apply {
            setOrder(order)
            setOnOrderUpdatedListener { updatedOrder ->
                val index = basketItems.indexOfFirst { it.id == updatedOrder.id }
                if (index != -1) {
                    basketItems[index] = updatedOrder
                    basketDetailAdapter.updateItems(basketItems)
                }
            }
        }.show(parentFragmentManager, "OrderEditPopup")
    }
}