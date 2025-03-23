import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.api.managers.TicketApiManager
import com.sumplier.app.data.enums.DateFormat
import com.sumplier.app.data.listener.ConfirmationListener
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.Ticket
import com.sumplier.app.data.model.TicketOrder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConfirmationPopup : DialogFragment() {

    private lateinit var currentAccount: CompanyAccount
    private var currentPage = 0 // 0: Selection, 1: Progress, 2: Success, 3: Fail
    private var confirmationListener: ConfirmationListener? = null
    private var orderList: ArrayList<TicketOrder> = ArrayList();
    private var onSuccess: (() -> Unit)? = null
    private var discountPrice: Double = 0.0
    private lateinit var etDiscountPrice: EditText
    private lateinit var tvDiscountError: TextView
    private lateinit var btnApplyDiscount: ConstraintLayout
    private var totalPrice: Double = 0.0

    private lateinit var tvTicketCode: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_confirmation, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPage(currentPage)

        tvTicketCode = view.findViewById(R.id.ticketCode)
        etDiscountPrice = view.findViewById(R.id.etDiscountPrice)
        tvDiscountError = view.findViewById(R.id.tvDiscountError)
        btnApplyDiscount = view.findViewById(R.id.btnApplyDiscount)

        view.findViewById<ConstraintLayout>(R.id.btnYes)?.setOnClickListener {
            showProgress()
            createSendOrder()

        }

        view.findViewById<ConstraintLayout>(R.id.btnTakePayment)?.setOnClickListener {
            dismiss()
        }

        view.findViewById<ImageView>(R.id.closeButton)?.setOnClickListener {
            dismiss()
        }

        view.findViewById<ConstraintLayout>(R.id.btnDiscount)?.setOnClickListener {
            currentPage = 4
            showPage(currentPage)
        }

        view.findViewById<ConstraintLayout>(R.id.btnApplyDiscount)?.setOnClickListener {
            val discountText = etDiscountPrice.text.toString()
            if (discountText.isNotEmpty()) {
                discountPrice = discountText.toDouble()
            }
            currentPage = 0
            showPage(currentPage)
        }

        calculateTotalPrice()

        etDiscountPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                validateDiscountAmount(s?.toString())
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createSendOrder() {
        val formatter = DateTimeFormatter.ofPattern(DateFormat.CLOUD_FORMAT.pattern)
        val createDateTime = LocalDateTime.now().format(formatter)
        val modifiedDateTime = LocalDateTime.now().format(formatter)

        // İndirim oranını hesapla
        val discountRate = if (totalPrice > 0) discountPrice / totalPrice else 0.0

        // Calculate prices...
        var totalPrice = 0.0
        var generalTotal = 0.0
        var taxTotal = 0.0


        val finalOrders:ArrayList<TicketOrder> = ArrayList()

        for (order in orderList) {

            //Calculate the exact discount...
            val orderDiscountAmount = order.totalPrice * discountRate

            totalPrice += order.totalPrice

            // Calculate the order new price
            order.discountPrice = orderDiscountAmount
            order.totalPrice -= orderDiscountAmount

            generalTotal += order.totalPrice
            taxTotal = (order.totalPrice / 100) * 10

            finalOrders.add(order)

        }

        val ticket = Ticket(
            id = 0,
            ticketCode = 0,
            companyCode = Config.getInstance().getCurrentCompany().companyCode,
            userCode = Config.getInstance().getCurrentUser().userCode,
            createDateTime = createDateTime,
            modifiedDateTime = modifiedDateTime,
            total = totalPrice,
            taxTotal = taxTotal,
            generalTotal = generalTotal,
            discountTotal = discountPrice,
            paymentType = "CASH",
            description = "CASH PAID",
            status = 0,
            resellerCode = Config.getInstance().getCurrentCompany().resellerCode,
            accountCode = currentAccount.accountCode,
            deviceCode = "DEV001",
            ticketOrders = finalOrders,
            accountName = currentAccount.accountName,
            paymentStatus = 0
        )

        val ticketApiManager = TicketApiManager()

        try {
            ticketApiManager.postTicket(ticket) {
                confirmationListener?.onConfirmed(finalOrders)

                tvTicketCode.text = it.toString()

            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun showPage(pageIndex: Int) {
        view?.apply {
            findViewById<ViewGroup>(R.id.selectionPage)?.visibility = if (pageIndex == 0) View.VISIBLE else View.GONE
            findViewById<ViewGroup>(R.id.progressPage)?.visibility = if (pageIndex == 1) View.VISIBLE else View.GONE
            findViewById<ViewGroup>(R.id.successPage)?.visibility = if (pageIndex == 2) View.VISIBLE else View.GONE
            findViewById<ViewGroup>(R.id.failPage)?.visibility = if (pageIndex == 3) View.VISIBLE else View.GONE
            findViewById<ViewGroup>(R.id.discountPage)?.visibility = if (pageIndex == 4) View.VISIBLE else View.GONE
        }
    }

    private fun showProgress() {
        currentPage = 1
        showPage(currentPage)
    }

    fun showSuccess(onSuccessCallback: () -> Unit) {
        currentPage = 2
        showPage(currentPage)
        onSuccess = onSuccessCallback
        Handler(Looper.getMainLooper()).postDelayed({
            onSuccess?.invoke()
            dismiss()
        }, 3000)
    }

    fun showFail() {
        currentPage = 3
        showPage(currentPage)
        Handler(Looper.getMainLooper()).postDelayed({
            dismiss()
        }, 1500)
    }

    fun setOrderList(orders: ArrayList<TicketOrder>) {
        orderList = orders
    }

    fun setCurrentAccount (account:CompanyAccount){
        currentAccount = account
    }

    fun setConfirmationListener(listener: ConfirmationListener) {
        confirmationListener = listener
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun calculateTotalPrice() {
        totalPrice = 0.0
        for (order in orderList) {
            totalPrice += order.totalPrice
        }
    }
    
    private fun validateDiscountAmount(discountText: String?) {
        if (discountText.isNullOrEmpty()) {
            tvDiscountError.visibility = View.GONE
            btnApplyDiscount.isEnabled = false
            return
        }
        
        try {
            val discountAmount = discountText.toDouble()
            if (discountAmount > totalPrice) {
                tvDiscountError.visibility = View.VISIBLE
                btnApplyDiscount.isEnabled = false
            } else {
                tvDiscountError.visibility = View.GONE
                btnApplyDiscount.isEnabled = true
            }
        } catch (e: NumberFormatException) {
            tvDiscountError.visibility = View.GONE
            btnApplyDiscount.isEnabled = false
        }
    }
}