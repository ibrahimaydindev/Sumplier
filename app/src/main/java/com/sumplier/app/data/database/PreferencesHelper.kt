package com.sumplier.app.data.database

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sumplier.app.data.enums.ConfigKey

object PreferencesHelper {
    private lateinit var sharedPreferences: SharedPreferences
    private val gson: Gson = Gson()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("sumplierData", Context.MODE_PRIVATE)
    }


    fun <T> saveData(key: ConfigKey, data: T) {
        val jsonString = gson.toJson(data)
        sharedPreferences.edit().putString(key.name, jsonString).apply()
    }


    fun <T> getData(key: ConfigKey, classType: Class<T>): T? {
        val jsonString = sharedPreferences.getString(key.name, null)
        return if (jsonString != null) {
            try {
                gson.fromJson(jsonString, classType)
            } catch (e: Exception) {
                Log.e("DataStorage", "Error parsing data: ${e.message}")
                null
            }
        } else {
            null
        }
    }


    fun <T> getListData(key: ConfigKey, classType: Class<T>): List<T>? {
        val jsonString = sharedPreferences.getString(key.name, null)
        return if (jsonString != null) {
            try {
                val type = TypeToken.getParameterized(List::class.java, classType).type
                gson.fromJson<List<T>>(jsonString, type)
            } catch (e: Exception) {
                Log.e("DataStorage", "Error parsing list data: ${e.message}")
                null
            }
        } else {
            null
        }
    }

    fun <T> saveOrUpdateData(key: ConfigKey, data: T) {
        val existingData = getData(key, data!!::class.java)
        if (existingData == null) {
            saveData(key, data)
        } else {

            saveData(key, data)
        }
    }

    fun clearData(key: ConfigKey) {
        sharedPreferences.edit().remove(key.name).apply()
    }

    fun clearAllData() {
        sharedPreferences.edit().clear().apply()
    }
}