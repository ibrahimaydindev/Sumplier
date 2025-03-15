import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    private lateinit var tvTicketCode: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_confirmation, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPage(currentPage)

        tvTicketCode = view.findViewById(R.id.ticketCode)

        view.findViewById<ConstraintLayout>(R.id.btnYes)?.setOnClickListener {
            showProgress()
            createSendOrder()

        }

        view.findViewById<ConstraintLayout>(R.id.btnNo)?.setOnClickListener {
            dismiss()
        }

        view.findViewById<ImageView>(R.id.closeButton)?.setOnClickListener {
            dismiss()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createSendOrder() {

        val formatter = DateTimeFormatter.ofPattern(DateFormat.CLOUD_FORMAT.pattern)

        val createDateTime = LocalDateTime.now().format(formatter)
        val modifiedDateTime = LocalDateTime.now().format(formatter)

        // Calculate prices...
        var totalPrice = 0.0
        var generalTotal = 0.0

        for (order:TicketOrder in orderList){

            totalPrice += order.totalPrice
            generalTotal += order.totalPrice //TODO

        }

        val ticket = Ticket(
            id = 0,
            ticketCode = 0,
            companyCode = Config.getInstance().getCurrentCompany().companyCode,
            userCode = Config.getInstance().getCurrentUser().userCode,
            createDateTime = createDateTime,
            modifiedDateTime = modifiedDateTime,
            total = totalPrice,
            taxTotal = 11.1,
            generalTotal = generalTotal,
            paymentType = "CASH",
            description = "CASH PAID",
            status = 0,
            resellerCode = Config.getInstance().getCurrentCompany().resellerCode,
            accountCode = currentAccount.accountCode,
            deviceCode = "DEV001",
            ticketOrders = orderList,
            accountName = currentAccount.accountName,
            paymentStatus = 0

        )

        val ticketApiManager = TicketApiManager()

        try {
            ticketApiManager.postTicket(ticket) {
                confirmationListener?.onConfirmed(orderList)

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
        }
    }

    fun showProgress() {
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
}