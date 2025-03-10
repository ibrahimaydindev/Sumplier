package com.sumplier.app.presentation.activity


import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.model.Company
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.User
import com.sumplier.app.presentation.popUp.AccountSelectionPopUp
import com.sumplier.app.presentation.listener.AccountSelectionListener

class MainActivity : AppCompatActivity() {

    private var company:Company? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        company = Config.getInstance().getCurrentCompany()
        user = Config.getInstance().getCurrentUser()

        setView()
        setButtonActions()
    }

    private fun setView(){

        findViewById<TextView>(R.id.tvCompanyName).text = company?.companyName ?:"CompanyName"
        findViewById<TextView>(R.id.tvUserName).text = user?.name ?:"UserName"

    }

    private fun setButtonActions(){

        findViewById<LinearLayout>(R.id.btnCreateOrder)?.setOnClickListener {

            showAccountPopup()
        }

        findViewById<LinearLayout>(R.id.buttonAccounts)?.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.buttonPastOrders)?.setOnClickListener {

            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.buttonNotifications)?.setOnClickListener {

            // notifications
        }

    }

    private fun showAccountPopup() {
        val accounts: List<CompanyAccount>? = Config.getInstance().getCompanyAccounts()

        if (accounts.isNullOrEmpty()) {
            return
        }

        val accountPopup = AccountSelectionPopUp.newInstance(accounts, object :
            AccountSelectionListener {
            override fun onAccountSelected(account: CompanyAccount) {
                // Seçilen hesabı işle
                Toast.makeText(this@MainActivity, "Selected: ${account.accountName}", Toast.LENGTH_SHORT).show()
                startActivityWithAccountId(account.id)
            }

            override fun onPopupDismissed() {
                // Popup kapatıldığında yapılacak işlemler
            }
        })

        accountPopup.show(supportFragmentManager, "AccountPopup")

    }

    fun startActivityWithAccountId(accountId: Long) {
        val intent = Intent(this, BasketActivity::class.java)
        intent.putExtra("account_id", accountId)  // accountId'yi Intent'e ekleyin
        startActivity(intent)
    }
}