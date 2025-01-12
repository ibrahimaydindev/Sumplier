package com.sumplier.app.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.sumplier.app.R
import com.sumplier.app.api.UserApiManager
import com.sumplier.app.app.Config
import com.sumplier.app.data.DataStorage
import com.sumplier.app.enums.ConfigKey
import com.sumplier.app.enums.ConfigState
import com.sumplier.app.model.Product
import kotlinx.coroutines.launch

internal enum class OnboardingPages {
    PROGRESS,
    LOGIN,
    RESET_PASSWORD_EMAIL,
    RESET_PASSWORD_CODE,
    NEW_PASSWORD,
    SUCCESS,
    FAIL
}

class OnboardingActivity : AppCompatActivity() {

    private var currentState = ConfigState.USER
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var btnLogin: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        
        viewFlipper = findViewById(R.id.viewFlipper)
        setUpLoginScreen();
        setupViews()
        
        processCurrentState()
        //setView(OnboardingPages.PROGRESS);

    }
    

    private fun setUpLoginScreen(){
        btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etEmail)?.text.toString()
            val password = findViewById<TextInputEditText>(R.id.etPassword)?.text.toString()
            
            when {
                email.isEmpty() -> showLoginError("Lütfen email adresinizi giriniz")
                password.isEmpty() -> showLoginError("Lütfen şifrenizi giriniz")
                else -> {
                    btnLogin.isEnabled = false
                    btnLogin.text = "Giriş yapılıyor..."
                    fetchUser(email, password, 1)
                }
            }
        }

        findViewById<TextView>(R.id.tvForgotPassword)?.setOnClickListener {
            setView(OnboardingPages.RESET_PASSWORD_EMAIL)
        }

    }
    private fun setupViews() {


        // Reset Password Email View
        findViewById<Button>(R.id.btnSendCode)?.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etEmail)?.text.toString()
            if (email.isNotEmpty()) {
                // Email'e kod gönderme işlemi
                setView(OnboardingPages.RESET_PASSWORD_CODE)
            }
        }

        findViewById<Button>(R.id.btnBack)?.setOnClickListener {
            setView(OnboardingPages.LOGIN)
        }

        // Reset Password Code View
        findViewById<Button>(R.id.btnVerifyCode)?.setOnClickListener {
            val code = findViewById<TextInputEditText>(R.id.etCode)?.text.toString()
            if (code.length == 6) {
                // Kod doğrulama işlemi
                setView(OnboardingPages.NEW_PASSWORD)
            }
        }

        findViewById<TextView>(R.id.tvResendCode)?.setOnClickListener {
            // Kodu tekrar gönderme işlemi
        }

        // New Password View
        findViewById<Button>(R.id.btnSavePassword)?.setOnClickListener {
            val newPassword = findViewById<TextInputEditText>(R.id.etNewPassword)?.text.toString()
            val confirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)?.text.toString()
            
            when {
                newPassword.isEmpty() -> showFailView("Lütfen yeni şifrenizi girin")
                confirmPassword.isEmpty() -> showFailView("Lütfen şifrenizi tekrar girin")
                newPassword != confirmPassword -> showFailView("Şifreler eşleşmiyor")
                else -> {
                    // Şifre değiştirme API çağrısı
                    // Başarılı olursa:
                    setView(OnboardingPages.SUCCESS)
                    // Başarısız olursa:
                    // showFailView("Şifre değiştirme işlemi başarısız oldu")
                }
            }
        }
    }
    
    private fun setView(page: OnboardingPages) {
        viewFlipper.displayedChild = when (page) {
            OnboardingPages.PROGRESS -> 0
            OnboardingPages.LOGIN -> 1
            OnboardingPages.RESET_PASSWORD_EMAIL -> 2
            OnboardingPages.RESET_PASSWORD_CODE -> 3
            OnboardingPages.NEW_PASSWORD -> 4
            OnboardingPages.SUCCESS -> 5
            OnboardingPages.FAIL -> 6
        }
    }
    
    private fun processCurrentState() {
        lifecycleScope.launch {
            try {
                when (currentState) {
                    ConfigState.USER -> checkSetUser()
                    //ConfigState.PRODUCTS -> fetchProductData()
                    ConfigState.COMPLETED -> navigateToMain()
                    else -> Log.e("Onboarding", "Bilinmeyen state: $currentState")
                }
            } catch (e: Exception) {
                Log.e("Onboarding", "State işlenirken hata: ${e.message}")
            }
        }
    }
    
    private fun checkSetUser() {

        if (Config.getInstance().getCurrentUser() != null){

            currentState = ConfigState.COMPLETED
            showToastMessage("Giriş başarılı")
            processCurrentState()
            return
        }else
            setView(OnboardingPages.LOGIN)

    }

    private fun fetchUser(email: String, password: String, loginType: Int) {
        val userApiManager = UserApiManager()
        userApiManager.loginUser(email, password, loginType) { user ->
            runOnUiThread {
                btnLogin.isEnabled = true
                btnLogin.text = "Giriş Yap"
                
                if (user != null && user.id != 0) {
                    Log.d("Onboarding", "User bilgileri başarıyla alındı")
                    Config.getInstance().setCurrentUser(user)
                    currentState = ConfigState.COMPLETED
                    showToastMessage("Giriş başarılı")
                    processCurrentState()
                } else {
                    setView(OnboardingPages.LOGIN)
                    showLoginError("Email veya şifre hatalı")
                }
            }
        }
    }
    
    //private suspend fun fetchProductData() {
    //    Log.d("Onboarding", "Ürün bilgileri çekiliyor...")
    //    val productResponse = ApiService.getProductState()
    //
    //    if (productResponse.isSuccessful) {
    //        Log.d("Onboarding", "Ürün bilgileri başarıyla alındı")
    //        DataStorage.saveData(ConfigKey.PRODUCTS, productResponse.data)
    //
    //        // Tüm işlemler tamamlandı
    //        currentState = ConfigState.COMPLETED
    //        processCurrentState()
    //    } else {
    //        Log.e("Onboarding", "Ürün bilgileri alınamadı: ${productResponse.error}")
    //        handleError("Ürün bilgileri alınamadı")
    //    }
    //}
    
    private fun navigateToMain() {
        Log.d("Onboarding", "Tüm veriler başarıyla yüklendi")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun showFailView(errorMessage: String) {
        setView(OnboardingPages.FAIL)
        findViewById<TextView>(R.id.tvFailMessage)?.text = errorMessage
        
        findViewById<Button>(R.id.btnRetry)?.setOnClickListener {
            setView(OnboardingPages.LOGIN) // veya başka bir sayfaya yönlendirme
        }
    }
    
    private fun handleError(message: String) {
        showFailView(message)
    }

    private fun showLoginError(message: String) {
        val tvLoginError = findViewById<TextView>(R.id.tvLoginError)
        tvLoginError?.apply {
            text = message
            visibility = View.VISIBLE
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}