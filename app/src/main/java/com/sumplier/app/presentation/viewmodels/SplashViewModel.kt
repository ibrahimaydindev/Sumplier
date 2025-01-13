package com.sumplier.app.presentation.viewmodels

import android.accounts.Account
import android.bluetooth.BluetoothClass
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumplier.app.data.api.managers.AccountApiManager
import com.sumplier.app.data.api.managers.CategoryApiManager
import com.sumplier.app.data.api.managers.CompanyLicenceApiManager
import com.sumplier.app.data.api.managers.DeviceApiManager
import com.sumplier.app.data.api.managers.ProductApiManager
import com.sumplier.app.data.api.managers.TicketApiManager
import com.sumplier.app.data.api.managers.UserApiManager
import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.CompanyLicence
import com.sumplier.app.data.model.Product
import com.sumplier.app.data.model.Ticket
import com.sumplier.app.data.model.User
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userApiManager: UserApiManager,
    private val accountApiManager: AccountApiManager,
    private val deviceApiManager: DeviceApiManager,
    private val ticketApiManager: TicketApiManager,
    private val companyLicenceApiManager: CompanyLicenceApiManager,
    private val productApiManager: ProductApiManager,
    private val categoryApiManager: CategoryApiManager
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<User?>>()
    val loginResult: LiveData<Result<User?>> get() = _loginResult

    private val _accountResult = MutableLiveData<Result<Account?>>()
    val accountResult: LiveData<Result<Account?>> get() = _accountResult

    private val _deviceResult = MutableLiveData<Result<BluetoothClass.Device?>>()
    val deviceResult: LiveData<Result<BluetoothClass.Device?>> get() = _deviceResult

    private val _productResult = MutableLiveData<Result<Product?>>()
    val productResult: LiveData<Result<Product?>> get() = _productResult

    private val _ticketResult = MutableLiveData<Result<Ticket?>>()
    val ticketResult: LiveData<Result<Ticket?>> get() = _ticketResult

    private val _categoryResult = MutableLiveData<Result<Category?>>()
    val categoryResult: LiveData<Result<Category?>> get() = _categoryResult

    private val _companyLicenceResult = MutableLiveData<Result<CompanyLicence?>>()
    val companyLicenceResult: LiveData<Result<CompanyLicence?>> get() = _companyLicenceResult

    private val _postUserResult = MutableLiveData<Result<Unit>>()
    val postUserResult: LiveData<Result<Unit>> get() = _postUserResult

    suspend fun loginUser(email: String, password: String, loginType: Int) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            try {
                val user = userApiManager.loginUser(email, password, loginType)
                _loginResult.value = if (user != null) {
                    Result.Success(user)
                } else {
                    Result.Error("User not found")
                }
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    suspend fun postUser(user: User) {
        viewModelScope.launch {
            _postUserResult.value = Result.Loading
            try {
                userApiManager.postUser(user)
                _postUserResult.value = Result.Success(Unit)
            } catch (e: Exception) {
                _postUserResult.value = Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}