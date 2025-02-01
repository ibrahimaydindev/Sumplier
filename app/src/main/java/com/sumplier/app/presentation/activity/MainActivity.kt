package com.sumplier.app.presentation.activity


import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.Company
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.Menu
import com.sumplier.app.data.model.Product
import com.sumplier.app.data.model.User
import com.sumplier.app.presentation.activity.listener.AccountSelectionListener

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

            // accounts
        }

        findViewById<LinearLayout>(R.id.buttonPastOrders)?.setOnClickListener {

            //past orders
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

        //val accountPopup = AccountPopup(accounts, object : AccountSelectionListener {
        //    override fun onAccountSelected(account: CompanyAccount) {
//
//
//
        //    }
//
        //    override fun onPopupDismissed() {
        //        println("Popup kapatıldı.")
        //    }
        //})
//
        //accountPopup.show(supportFragmentManager, "AccountPopup")
    }
}