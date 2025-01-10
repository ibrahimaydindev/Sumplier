package com.sumplier.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sumplier.app.R
import com.sumplier.app.api.AccountApiManager
import com.sumplier.app.api.CategoryApiManager
import com.sumplier.app.api.CompanyLicenceApiManager
import com.sumplier.app.api.DeviceApiManager
import com.sumplier.app.api.ProductApiManager
import com.sumplier.app.api.TicketApiManager
import com.sumplier.app.api.UserApiManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = "test@test.com"
        val password = "1" // company code as example
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

        val productApiManager = ProductApiManager()

        productApiManager.getProductAll(password) { products ->
            if (!products.isNullOrEmpty()) {
                Log.d("MainActivity", "Product lar başarıyla alındı - Toplam: ${products.size}")
                products.forEach { product ->
                    Log.d("MainActivity", "Product: ${product.productName}")
                }
            } else {
                val errorMessage = if (products == null) {
                    "Product verileri alınırken bağlantı hatası oluştu"
                } else {
                    "Hiç product bulunamadı"
                }
                Log.e("MainActivity", errorMessage)
            }
        }

        val licenseApiManager = CompanyLicenceApiManager()

        licenseApiManager.getCompanyLicences(password) { licences ->
            if (!licences.isNullOrEmpty()) {
                Log.d("MainActivity", "License lar başarıyla alındı - Toplam: ${licences.size}")
                licences.forEach { licence ->
                    Log.d("MainActivity", "License: $licence")
                }
            } else {
                val errorMessage = if (licences == null) {
                    "License verileri alınırken bağlantı hatası oluştu"
                } else {
                    "Hiç License bulunamadı"
                }
                Log.e("MainActivity", errorMessage)
            }
        }

        val deviceApiManager = DeviceApiManager()

        deviceApiManager.getCompanyLicences(password) { devices ->
            if (!devices.isNullOrEmpty()) {
                Log.d("MainActivity", "Device lar başarıyla alındı - Toplam: ${devices.size}")
                devices.forEach { device ->
                    Log.d("MainActivity", "Device: $device")
                }
            } else {
                val errorMessage = if (devices == null) {
                    "Device lar alınırken bağlantı hatası oluştu"
                } else {
                    "Hiç Device bulunamadı"
                }
                Log.e("MainActivity", errorMessage)
            }
        }

        val accountApiManager = AccountApiManager()

        accountApiManager.getAccountAll(password) { accounts ->
            if (!accounts.isNullOrEmpty()) {
                Log.d("MainActivity", "Account lar başarıyla alındı - Toplam: ${accounts.size}")
                accounts.forEach { account ->
                    Log.d("MainActivity", "Account: $account")
                }
            } else {
                val errorMessage = if (accounts == null) {
                    "Account lar alınırken bağlantı hatası oluştu"
                } else {
                    "Hiç Account bulunamadı"
                }
                Log.e("MainActivity", errorMessage)
            }
        }


        val categoryApiManager = CategoryApiManager()

        categoryApiManager.getCategory(password) { categories ->
            if (!categories.isNullOrEmpty()) {
                Log.d("MainActivity", "Category ler başarıyla alındı - Toplam: ${categories.size}")
                categories.forEach { category ->
                    Log.d("MainActivity", "Account: $category")
                }
            } else {
                val errorMessage = if (categories == null) {
                    "Category ler alınırken bağlantı hatası oluştu"
                } else {
                    "Hiç Category bulunamadı"
                }
                Log.e("MainActivity", errorMessage)
            }
        }



    }
}