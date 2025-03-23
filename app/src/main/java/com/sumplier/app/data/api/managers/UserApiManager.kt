package com.sumplier.app.data.api.managers

import android.util.Log
import com.sumplier.app.data.api.apiservice.UserApiService
import com.sumplier.app.data.api.retrofit.RetrofitClient
import com.sumplier.app.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserApiManager {
    private val userApiService: UserApiService = RetrofitClient.getClient().create(UserApiService::class.java)

    fun loginUser(email: String, password: String, onResult: (User?) -> Unit) {

        val call = userApiService.getUserLogin(email, password)

        Log.d("UserApiManager", "Request URL: ${call.request().url()}")

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        onResult(user)
                    } else {
                        onResult(null)
                    }
                } else {
                    Log.e("UserApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("UserApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

}