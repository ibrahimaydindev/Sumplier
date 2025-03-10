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
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.api.managers.TicketApiManager
import com.sumplier.app.data.model.Ticket
import com.sumplier.app.data.model.TicketOrder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConfirmationPopup : DialogFragment() {
    interface OnConfirmationListener {
        fun onConfirmed(orders: List<TicketOrder>, ticketCode: Long?)
    }

    private var currentPage = 0 // 0: Selection, 1: Progress, 2: Success, 3: Fail
    private var confirmationListener: OnConfirmationListener? = null
    private var orderList: List<TicketOrder> = emptyList()
    private var onSuccess: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_confirmation, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPage(currentPage)

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

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        val createDateTime = LocalDateTime.now().format(formatter)
        val modifiedDateTime = LocalDateTime.now().format(formatter)

        val ticket = Ticket(
            ticketCode = 0,
            companyCode = Config.getInstance().getCurrentCompany()?.companyCode,
            userCode = 0,
            createDateTime = createDateTime,
            modifiedDateTime = modifiedDateTime,
            total = 111.0,
            taxTotal = 0.0,
            generalTotal = 111.0,
            paymentType = "CASH",
            description = "CASH PAID",
            status = 0,
            resellerCode = Config.getInstance().getCurrentCompany()?.resellerCode,
            accountCode = Config.getInstance().getCurrentUser()?.id,  // TODO
            deviceCode = "DEV001"

        )

        val ticketApiManager = TicketApiManager()

        try {
            ticketApiManager.postTicket(ticket) { ticket1 ->

                if (ticket1 != null) {
                    confirmationListener?.onConfirmed(orderList, ticket1.ticketCode?.toLong())
                }
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

    fun setOrderList(orders: List<TicketOrder>) {
        orderList = orders
    }

    fun setConfirmationListener(listener: OnConfirmationListener) {
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