package com.sumplier.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sumplier.app.R
import com.sumplier.app.api.TicketApiManager
import com.sumplier.app.api.UserApiManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = "test@test.com"
        val password = "1"
        val loginType = 1

        val userApiManager = UserApiManager()
        userApiManager.loginUser(email, password, loginType) { response ->
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

        val ticketApiManager = TicketApiManager()

        ticketApiManager.getTicketAll(password) { tickets ->
            if (tickets != null && tickets.isNotEmpty()) {
                Log.d("MainActivity", "Ticketlar başarıyla alındı - Toplam: ${tickets.size}")
                tickets.forEach { ticket ->
                    Log.d("MainActivity", "Ticket: $ticket")
                }
            } else {
                val errorMessage = if (tickets == null) {
                    "Ticket verileri alınırken bağlantı hatası oluştu"
                } else {
                    "Hiç ticket bulunamadı"
                }
                Log.e("MainActivity", errorMessage)
            }
        }




    }
}