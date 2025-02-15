import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.sumplier.app.R
import com.sumplier.app.data.model.TicketOrder

class ConfirmationPopup : DialogFragment() {
    private var currentPage = 0 // 0: Selection, 1: Progress, 2: Success, 3: Fail
    private var onConfirmed: ((List<TicketOrder>) -> Unit)? = null
    private var orderList: List<TicketOrder> = emptyList()
    private var onSuccess: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPage(currentPage)

        view.findViewById<ConstraintLayout>(R.id.btnYes)?.setOnClickListener {
            showProgress()
            onConfirmed?.invoke(orderList)
        }

        view.findViewById<ConstraintLayout>(R.id.btnNo)?.setOnClickListener {
            dismiss()
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

    fun setOnConfirmedListener(listener: (List<TicketOrder>) -> Unit) {
        onConfirmed = listener
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