package com.sumplier.app.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sumplier.app.data.api.managers.AccountApiManager
import com.sumplier.app.data.api.managers.CategoryApiManager
import com.sumplier.app.data.api.managers.CompanyLicenceApiManager
import com.sumplier.app.data.api.managers.DeviceApiManager
import com.sumplier.app.data.api.managers.ProductApiManager
import com.sumplier.app.data.api.managers.TicketApiManager
import com.sumplier.app.data.api.managers.UserApiManager
import com.sumplier.app.data.api.retrofit.ApiModule
import com.sumplier.app.data.model.User
import com.sumplier.app.databinding.ActivityReceptionBinding
import com.sumplier.app.presentation.viewmodels.GenericViewModelFactory
import com.sumplier.app.presentation.viewmodels.Result
import com.sumplier.app.presentation.viewmodels.SplashViewModel
import kotlinx.coroutines.launch

class ReceptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceptionBinding
    private lateinit var splashViewModel: SplashViewModel
    private val totalApiCalls = 2
    private var successfulCalls = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashViewModelFactory = GenericViewModelFactory {
            SplashViewModel(
                userApiManager = UserApiManager(ApiModule.userApiService),
                accountApiManager = AccountApiManager(ApiModule.accountApiService),
                deviceApiManager = DeviceApiManager(ApiModule.deviceApiService),
                ticketApiManager = TicketApiManager(ApiModule.ticketApiService),
                companyLicenceApiManager = CompanyLicenceApiManager(ApiModule.companyLicence),
                productApiManager = ProductApiManager(ApiModule.productApiService),
                categoryApiManager = CategoryApiManager(ApiModule.categoryApiService)
            )
        }

        splashViewModel =
            ViewModelProvider(this, splashViewModelFactory)[SplashViewModel::class.java]

        observeViewModel()
        startApiCalls()
    }

    private fun observeViewModel() {
        splashViewModel.loginResult.observe(this) { result ->
            handleApiResult(result)
        }

        splashViewModel.postUserResult.observe(this) { result ->
            handleApiResult(result)
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

                )
            splashViewModel.postUser(user)
        }
    }
}