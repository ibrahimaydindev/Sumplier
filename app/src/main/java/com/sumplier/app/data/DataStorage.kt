package com.sumplier.app.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sumplier.app.enums.ConfigKey

object DataStorage {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("AppStorage", Context.MODE_PRIVATE)
    }

    fun <T> saveData(key: ConfigKey, data: T) {
        val gson = Gson()
        val jsonString = gson.toJson(data)
        sharedPreferences.edit().putString(key.name, jsonString).apply()
    }

    fun <T> getData(key: ConfigKey, classType: Class<T>): T? {
        val jsonString = sharedPreferences.getString(key.name, null)
        return try {
            if (jsonString != null) {
                Gson().fromJson(jsonString, classType)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun <T> getListData(key: ConfigKey, classType: Class<T>): List<T>? {
        val jsonString = sharedPreferences.getString(key.name, null)
        return try {
            if (jsonString != null) {
                val type = TypeToken.getParameterized(List::class.java, classType).type
                Gson().fromJson(jsonString, type)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun clearData(key: ConfigKey) {
        sharedPreferences.edit().remove(key.name).apply()
    }

    fun clearAllData() {
        sharedPreferences.edit().clear().apply()
    }
}