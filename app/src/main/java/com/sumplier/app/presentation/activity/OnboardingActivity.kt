package com.sumplier.app.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.sumplier.app.R
import com.sumplier.app.data.enums.ConfigState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("Onboarding", "onCreate")

        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        viewFlipper = findViewById(R.id.viewFlipper)
        setUpCompanyLoginScreen()
        setUpUserLoginScreen()
        setupViews()

        processCurrentState()


    }


    private fun setUpCompanyLoginScreen() {

        val loginButtonText: TextView = findViewById(R.id.companyLogin_button_text)


        btnCompanyLogin = findViewById(R.id.btnCompanyLogin)
        btnCompanyLogin.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etEmail)?.text.toString()
            val password = findViewById<TextInputEditText>(R.id.etPassword)?.text.toString()

            when {
                else -> {
                    btnCompanyLogin.isEnabled = false
                    loginButtonText.text = "Giriş yapılıyor..."
                    fetchCompany(email, password, 1)
                }
            }
        }

        findViewById<TextView>(R.id.tvForgotPassword)?.setOnClickListener {
            setView(OnboardingPages.RESET_PASSWORD_EMAIL)
        }

    }

    private fun setUpUserLoginScreen() {

        val loginButtonText: TextView = findViewById(R.id.userLogin_button_text)


        btnUserLogin = findViewById(R.id.btnLoginUser)
        btnUserLogin.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etEmail)?.text.toString()
            val password = findViewById<TextInputEditText>(R.id.etPassword)?.text.toString()

            when {
                else -> {
                    btnUserLogin.isEnabled = false
                    loginButtonText.text = "Giriş yapılıyor..."
                    fetchCompany(email, password, 1)
                }
            }
        }

        findViewById<TextView>(R.id.tvForgotPasswordUser)?.setOnClickListener {
            setView(OnboardingPages.RESET_PASSWORD_EMAIL)
        }

    }

    private fun setupViews() {

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
                    ConfigState.COMPANY -> checkSetCompany()
                    ConfigState.USER -> userLogin()
                    //ConfigState.PRODUCTS -> fetchProductData()
                    ConfigState.COMPLETED -> navigateToMain()
                    else -> Log.e("Onboarding", "Bilinmeyen state: $currentState")
                }
            } catch (e: Exception) {
                Log.e("Onboarding", "State işlenirken hata: ${e.message}")
            }
        }
    }

    private suspend fun checkSetCompany() {

        setView(OnboardingPages.PROGRESS)
        delay(4000)
        setView(OnboardingPages.COMPANY_LOGIN)
        delay(4000)

        currentState = ConfigState.USER
        processCurrentState()


    }

    private suspend fun userLogin() {

        setView(OnboardingPages.PROGRESS)
        delay(4000)

        setView(OnboardingPages.USER_LOGIN)
        delay(4000)

        currentState = ConfigState.COMPLETED
        processCurrentState()
    }

    private fun fetchCompany(email: String, password: String, loginType: Int) {

    }


    private suspend fun navigateToMain() {

        delay(4000)

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