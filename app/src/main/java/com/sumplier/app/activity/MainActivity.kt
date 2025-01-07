package com.sumplier.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.sumplier.app.R
import com.sumplier.app.api.ApiManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val email = "test@test.com"
        val password = "1"
        val loginType = 1

        val apiManager = ApiManager()
        apiManager.loginUser(email, password, loginType) { response ->
            if (response != null && response.id != 0) {
                Log.d("MainActivity", "Login başarılı - Kullanıcı: $response")
            } else {
                val errorMessage = when {
                    response == null -> "Bağlantı hatası"
                    response.id == 0 -> "Geçersiz kullanıcı bilgileri"
                    else -> "Bilinmeyen hata"
                }
                Log.e("MainActivity", "Login başarısız: $errorMessage")
            }
        }

    }
}