package com.sumplier.app.api

import android.util.Log
import com.sumplier.app.data.User
import com.sumplier.app.interfaces.apiService.UserApiService
import com.sumplier.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserApiManager {
    private val userApiService: UserApiService = RetrofitClient.getClient().create(UserApiService::class.java)

    fun loginUser(email: String, password: String, loginType: Int, onResult: (User?) -> Unit) {

        val call = userApiService.getUserLogin(email, password, loginType)
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