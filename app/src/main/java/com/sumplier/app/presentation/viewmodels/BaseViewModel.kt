package com.sumplier.app.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _state = MutableLiveData<Result<Any>>()
    val state: LiveData<Result<Any>> get() = _state

    protected fun <T> handleRequest(
        apiCall: suspend () -> T?,
        onSuccess: (T?) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            _state.postValue(Result.Loading)
            try {
                val result = apiCall()
                _state.postValue(Result.Success(result))
                onSuccess(result)
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Unknown error"
                _state.postValue(Result.Error(message))
                onError(message)
            }
        }
    }
}
