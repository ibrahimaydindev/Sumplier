import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.sumplier.app.R

class WarningPopup(
    private val warningText: String,
    private val warningIcon: Int, // Resource ID for icon
    private val yesButtonText: String = "Evet",
    private val noButtonText: String = "Hayır",
    private val showNoButton: Boolean = true
) : DialogFragment() {

    private var onYesClicked: (() -> Unit)? = null
    private var onNoClicked: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_warning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.warningText).text = warningText
        view.findViewById<ImageView>(R.id.warningIcon).setImageResource(warningIcon)

        view.findViewById<TextView>(R.id.yesButtonText).text = yesButtonText
        view.findViewById<TextView>(R.id.noButtonText).text = noButtonText
        
        view.findViewById<ConstraintLayout>(R.id.btnNo).apply {
            visibility = if (showNoButton) View.VISIBLE else View.GONE
            setOnClickListener {
                onNoClicked?.invoke()
                dismiss()
            }
        }

        view.findViewById<ConstraintLayout>(R.id.btnYes).setOnClickListener {
            onYesClicked?.invoke()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    fun setOnYesClickListener(listener: () -> Unit) {
        onYesClicked = listener
    }

    fun setOnNoClickListener(listener: () -> Unit) {
        onNoClicked = listener
    }
}