package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.UserApiService
import com.sumplier.app.data.model.User

class UserApiManager(private val userApiService: UserApiService) {

    suspend fun loginUser(email: String, password: String, loginType: Int): User? {
        return try {
            val response = userApiService.getUserLogin(email, password, loginType)
            if (response.isSuccessful) {
                val user = response.body()
                if (user?.id != 0) {
                    user
                } else {
                    null
                }
            } else {
                Log.e("UserApiManager", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("UserApiManager", "Failure: ${e.localizedMessage}", e)
            null
        }
    }

    suspend fun postUser(user: User) {
        try {
            val response = userApiService.postUserLogin(user)
            if (response.isSuccessful) {
                Log.d("UserApiManager", "User posted successfully: ${response.body()}")
            } else {
                Log.e("UserApiManager", "Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("UserApiManager", "Failure: ${e.localizedMessage}", e)
        }
    }
}
