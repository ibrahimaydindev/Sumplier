package com.sumplier.app.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sumplier.app.R
import com.sumplier.app.api.UserApiManager
import com.sumplier.app.app.Config
import com.sumplier.app.data.DataStorage
import com.sumplier.app.enums.ConfigKey
import com.sumplier.app.enums.ConfigState
import com.sumplier.app.model.Product
import kotlinx.coroutines.launch

class OnboardingActivity : AppCompatActivity() {
    private var currentState = ConfigState.USER
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        
        processCurrentState()
    }
    
    private fun processCurrentState() {
        lifecycleScope.launch {
            try {
                when (currentState) {
                    ConfigState.USER -> fetchUserData()
                    //ConfigState.PRODUCTS -> fetchProductData()
                    ConfigState.COMPLETED -> navigateToMain()
                    else -> Log.e("Onboarding", "Bilinmeyen state: $currentState")
                }
            } catch (e: Exception) {
                Log.e("Onboarding", "State işlenirken hata: ${e.message}")
            }
        }
    }
    
    private suspend fun fetchUserData() {
        Log.d("Onboarding", "User bilgileri çekiliyor...")

        val email = "test@test.com"
        val password = "1" // company code as example
        val loginType = 1

        if (Config.getInstance().getCurrentUser() != null){

            Log.d("Onboarding", "Zaten user vardı api çağrısı atlandı")

            currentState = ConfigState.COMPLETED
            processCurrentState()
            return
        }


        val userApiManager = UserApiManager()
        userApiManager.loginUser(email, password, loginType) { response ->
            if (response != null && response.id != 0) {

                Log.d("Onboarding", "User bilgileri başarıyla alındı")

                Config.getInstance().setCurrentUser(response)

                currentState = ConfigState.COMPLETED
                processCurrentState()

            } else {
                val errorMessage = when {
                    response == null -> "Bağlantı hatası"
                    else -> "Bilinmeyen hata"
                }
                Log.e("Onboarding", "User bilgileri alınamadı: $errorMessage")
                handleError("User bilgileri alınamadı")
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
    
    private fun handleError(message: String) {
        // Hata durumunda kullanıcıya gösterilecek dialog veya retry mekanizması
    }
}