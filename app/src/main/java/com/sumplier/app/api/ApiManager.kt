package com.sumplier.app.api

import android.util.Log
import com.sumplier.app.data.User
import com.sumplier.app.interfaces.ApiService
import com.sumplier.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager {
    private val apiService: ApiService = RetrofitClient.getClient().create(ApiService::class.java)

    fun loginUser(email: String, password: String, loginType: Int, onResult: (User?) -> Unit) {

        val call = apiService.getUserLogin(email, password, loginType)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user?.id != 0) {
                        onResult(user)
                    } else {
                        onResult(null)
                    }
                } else {
                    Log.e("ApiManager", "Error: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("ApiManager", "Failure: ${t.message}")
                onResult(null)
            }
        })
    }

}