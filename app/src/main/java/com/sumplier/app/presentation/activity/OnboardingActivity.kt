package com.sumplier.app.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.api.managers.AccountApiManager
import com.sumplier.app.data.api.managers.CategoryApiManager
import com.sumplier.app.data.api.managers.CompanyApiManager
import com.sumplier.app.data.api.managers.MenuApiManager
import com.sumplier.app.data.api.managers.ProductApiManager
import com.sumplier.app.data.api.managers.UserApiManager
import com.sumplier.app.data.database.PreferencesHelper
import com.sumplier.app.data.enums.ConfigKey
import com.sumplier.app.data.enums.ConfigState
import com.sumplier.app.data.model.Company
import com.sumplier.app.data.model.User
import kotlinx.coroutines.launch

internal enum class OnboardingPages {
    PROGRESS,
    COMPANY_LOGIN,
    USER_LOGIN,
    RESET_PASSWORD_EMAIL,
    RESET_PASSWORD_CODE,
    NEW_PASSWORD,

}

class OnboardingActivity : AppCompatActivity() {

    private var currentState = ConfigState.COMPANY
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var btnCompanyLogin: ConstraintLayout
    private lateinit var btnUserLogin: ConstraintLayout

    private lateinit var btnUserLoginText: TextView
    private lateinit var btnCompanyLoginText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("Onboarding", "onCreate")

        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        viewFlipper = findViewById(R.id.viewFlipper)

        setupViews()
        processCurrentState()


    }


    private fun setUpCompanyLoginScreen() {

        btnCompanyLogin = findViewById(R.id.btnCompanyLogin)
        btnCompanyLoginText = findViewById(R.id.companyLogin_button_text)
        btnCompanyLogin.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etEmail)?.text.toString()
            val password = findViewById<TextInputEditText>(R.id.etPassword)?.text.toString()

            when {
                else -> {
                    btnCompanyLogin.isEnabled = false
                    btnCompanyLoginText.text = "Giriş yapılıyor..."
                    fetchCompany(email, password)
                }
            }
        }

        findViewById<TextView>(R.id.tvForgotPassword)?.setOnClickListener {
            setView(OnboardingPages.RESET_PASSWORD_EMAIL)
        }

    }

    private fun setUpUserLoginScreen() {

        btnUserLogin = findViewById(R.id.btnLoginUser)
        btnUserLoginText = findViewById(R.id.userLogin_button_text)
        btnUserLogin.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etEmailUser)?.text.toString()
            val password = findViewById<TextInputEditText>(R.id.etPasswordUser)?.text.toString()

            when {
                else -> {
                    btnUserLogin.isEnabled = false
                    btnUserLoginText.text = "Giriş yapılıyor..."
                    fetchUser(email, password)
                }
            }
        }

        findViewById<TextView>(R.id.tvForgotPasswordUser)?.setOnClickListener {
            setView(OnboardingPages.RESET_PASSWORD_EMAIL)
        }

    }

    private fun setupViews() {

        setUpCompanyLoginScreen()
        setUpUserLoginScreen()

        // Reset Password Email View
        findViewById<Button>(R.id.btnSendCode)?.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etEmail)?.text.toString()
            if (email.isNotEmpty()) {
                setView(OnboardingPages.RESET_PASSWORD_CODE)
            }
        }

        findViewById<Button>(R.id.btnBack)?.setOnClickListener {
            setView(OnboardingPages.USER_LOGIN)
        }

    }

    private fun setView(page: OnboardingPages) {

        viewFlipper.setInAnimation(this, R.anim.slide_in_right)
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left)
        
        viewFlipper.displayedChild = when (page) {
            OnboardingPages.COMPANY_LOGIN -> 0
            OnboardingPages.PROGRESS -> 1
            OnboardingPages.USER_LOGIN -> 2
            OnboardingPages.RESET_PASSWORD_EMAIL -> 3
            OnboardingPages.RESET_PASSWORD_CODE -> 4
            OnboardingPages.NEW_PASSWORD -> 5
        }
    }

    private fun processCurrentState() {
        lifecycleScope.launch {
            try {
                Log.e("Onboarding", "state: $currentState")

                when (currentState) {
                    ConfigState.COMPANY -> handleCompanyLogin()
                    ConfigState.USER -> handleUserLogin()
                    ConfigState.MENUS -> fetchMenus()
                    ConfigState.CATEGORIES -> fetchCategories()
                    ConfigState.PRODUCTS -> fetchProducts()
                    ConfigState.ACCOUNT -> fetchAccounts()
                    ConfigState.COMPLETED -> navigateToMain()
                    else -> Log.e("Onboarding", "Bilinmeyen state: $currentState")
                }
            } catch (e: Exception) {
                Log.e("Onboarding", "State işlenirken hata: ${e.message}")
            }
        }
    }

    private fun fetchMenus(){

        val company : Company? = Config.getInstance().getCurrentCompany()

        if (company != null){

            val menuApiManager = MenuApiManager()

            try {
                menuApiManager.getMenus(company.companyCode, company.resellerCode) { menus ->

                    if (menus != null) {

                        Log.i("Onboarding", "menus : $menus")

                        PreferencesHelper.saveData(ConfigKey.MENU, menus)
                        currentState = ConfigState.CATEGORIES
                        processCurrentState()
                    } else {
                        showError("Menu alınırken bir hata oluştu")

                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun fetchAccounts(){

        val company : Company? = Config.getInstance().getCurrentCompany()

        if (company != null){

            val accountApiManager = AccountApiManager()

            try {
                accountApiManager.getAccounts(company.companyCode, company.resellerCode) { accounts ->

                    if (accounts != null) {

                        Log.i("Onboarding", "acounts : $accounts")

                        PreferencesHelper.saveData(ConfigKey.COMPANY_ACCOUNT, accounts)
                        currentState = ConfigState.COMPLETED
                        processCurrentState()
                    } else {
                        showError("Account alınırken bir hata oluştu")
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchCategories(){

        val company : Company? = Config.getInstance().getCurrentCompany()

        if (company != null){

            val categoryApiManager = CategoryApiManager()

            try {
                categoryApiManager.getCategories(company.companyCode, company.resellerCode) { categories ->

                    if (categories != null) {

                        Log.i("Onboarding", "categories : $categories")

                        PreferencesHelper.saveData(ConfigKey.CATEGORY, categories)
                        currentState = ConfigState.PRODUCTS
                        processCurrentState()
                    } else {
                        showError("Categories alınırken bir hata oluştu")
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchProducts(){

        val company : Company? = Config.getInstance().getCurrentCompany()

        if (company != null){

            val productApiManager = ProductApiManager();

            try {
                productApiManager.getProductsAll(company.companyCode, company.resellerCode) { products ->

                    if (products != null) {

                        Log.i("Onboarding", "products : $products")

                        PreferencesHelper.saveData(ConfigKey.PRODUCT, products)
                        currentState = ConfigState.ACCOUNT
                        processCurrentState()
                    } else {
                        showError("Products alınırken bir hata oluştu")
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun fetchCompany(email: String, password: String) {

        val companyApiManager = CompanyApiManager()

        try {
            companyApiManager.loginCompany(email, password) { company ->

                if (company != null) {
                    PreferencesHelper.saveData(ConfigKey.COMPANY, company)
                    currentState = ConfigState.USER
                    processCurrentState()
                } else {
                    showError("Email veya şifre hatalı")
                    btnCompanyLogin.isEnabled = true
                    btnCompanyLoginText.text = "Giriş Yap"
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun fetchUser(email: String, password: String) {

        val userApiManager = UserApiManager()

        try {
            userApiManager.loginUser(email, password) { user ->

                if (user != null) {
                    PreferencesHelper.saveData(ConfigKey.USER, user)
                    currentState = ConfigState.MENUS
                    setView(OnboardingPages.PROGRESS)
                    showToastMessage("Giriş başarılı")
                    processCurrentState()
                } else {
                    showError("Email veya şifre hatalı")
                    btnUserLogin.isEnabled = true
                    btnUserLoginText.text = "Giriş Yap"
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun handleCompanyLogin() {

        val company: Company? = PreferencesHelper.getData(ConfigKey.COMPANY, Company::class.java)

        if (company != null) {
            currentState = ConfigState.USER
            processCurrentState()
            return
        }

    }

    private fun handleUserLogin() {

        setView(OnboardingPages.USER_LOGIN)

        //val user: User? = PreferencesHelper.getData(ConfigKey.USER, User::class.java)
//
        //if (user != null) {
        //    currentState = ConfigState.MENUS
        //    processCurrentState()
        //    return
        //}

    }


    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Hata")
            .setMessage(message)
            .setPositiveButton("Tamam") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    private fun navigateToMain() {


        Config.getInstance().checkSetMenus()
        Config.getInstance().checkSetCategories()
        Config.getInstance().checkSetProducts()
        Config.getInstance().checkSetAccounts()


        Log.d("Onboarding", "Tüm veriler başarıyla yüklendi")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showFailView(errorMessage: String) {

        findViewById<TextView>(R.id.tvFailMessage)?.text = errorMessage

        findViewById<Button>(R.id.btnRetry)?.setOnClickListener {
            setView(OnboardingPages.USER_LOGIN) // veya başka bir sayfaya yönlendirme
        }
    }


    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}