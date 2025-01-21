package com.sumplier.app.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sumplier.app.data.api.retrofit.ApiModule
import com.sumplier.app.data.database.PreferencesHelper
import com.sumplier.app.data.enums.ConfigKey
import com.sumplier.app.data.model.User
import com.sumplier.app.databinding.ActivityReceptionBinding
import com.sumplier.app.presentation.viewmodels.Result
import com.sumplier.app.presentation.viewmodels.SplashViewModel
import kotlinx.coroutines.launch

class ReceptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceptionBinding
    private lateinit var splashViewModel: SplashViewModel
    private val totalApiCalls = 2
    private var successfulCalls = 0
    private lateinit var sharedData: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceptionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // startApiCalls()

        binding.loginButton.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleApiResult(result: Result<*>) {
        when (result) {
            is Result.Loading -> {
            }

            is Result.Success -> {
                successfulCalls++
                updateProgress()
                checkAllCallsCompleted()
            }

            is Result.Error -> {
                Toast.makeText(this, "Hata: ${result.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateProgress() {
        val progress = (successfulCalls.toFloat() / totalApiCalls) * 100
        binding.progressBar.progress = progress.toInt()
        binding.progressText.text = "${progress.toInt()}%"
    }

    private fun checkAllCallsCompleted() {
        if (successfulCalls == totalApiCalls) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun startApiCalls() {
        getUser()
        postUser()
    }

    private fun getUser() {
        binding.loginButton.setOnClickListener {

        }

        lifecycleScope.launch {
            splashViewModel.loginUser("test@test.com", "1", 1)
        }
    }

    private fun postUser() {

        lifecycleScope.launch {
            val user = User(
                id = 0,
                name = "John",
                surname = "Doe",
                email = "john.doe@example.com",
                password = "password123",
                loginType = 1,
                isActive = true,
                companyCode = 1,
                roleCode = "a",
                resellerCode = "a"

                )
            splashViewModel.postUser(user)
        }
    }

    private fun handleLogin() {

        val email = binding.userName.text.toString().trim()
        val password = binding.userPassword.text.toString().trim()

        var isValid = true

        if (!isValidEmail(email)) {
            showError("Geçerli Mail giriniz!")
            isValid = false
        }

        if (!isValidPassword(password)) {
            showError("Geçerli Parola giriniz!")
            isValid = false
        }

        if (isValid) {
            lifecycleScope.launch {
                try {

                    val response = ApiModule.userApiService.getUserLogin(email, password, 1)

                    if (response.isSuccessful) {
                        val user = response.body()

                        if (user?.id != 0) {
                            sharedData.saveData(ConfigKey.USER, user)
                        } else {
                            showError("Kullanıcı Bulunamadı")
                        }
                    } else {
                        showError("Bağlantı Başarısız")
                    }

                } catch (e: Exception) {
                    showError("Ağ Hatası")
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty()
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

    
}