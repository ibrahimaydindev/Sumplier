package com.sumplier.app.app

import com.sumplier.app.data.database.PreferencesHelper
import com.sumplier.app.data.enums.ConfigKey
import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.Product
import com.sumplier.app.data.model.User

class Config private constructor() {

    private val cachedData = mutableMapOf<ConfigKey, Any?>()

    companion object {
        @Volatile
        private var instance: Config? = null

        fun getInstance(): Config {
            return instance ?: synchronized(this) {
                instance ?: Config().also { instance = it }
            }
        }
    }

    private fun <T> getDataFromPrefs(key: ConfigKey, classType: Class<T>): T? {
        return cachedData[key] as? T ?: PreferencesHelper.getData(key, classType)?.also {
            cachedData[key] = it
        }
    }

    private fun <T> setDataToPrefs(key: ConfigKey, data: T?) {
        cachedData[key] = data
        PreferencesHelper.saveData(key, data)
    }

    fun getCurrentUser(): User? = getDataFromPrefs(ConfigKey.USER, User::class.java)

    fun setCurrentUser(user: User?) = setDataToPrefs(ConfigKey.USER, user)

    fun getCurrentCategory(): Category? = getDataFromPrefs(ConfigKey.CATEGORY, Category::class.java)

    fun setCurrentCategory(category: Category?) = setDataToPrefs(ConfigKey.CATEGORY, category)

    fun getCurrentProductList(): List<*>? = getDataFromPrefs(ConfigKey.PRODUCT, List::class.java)

    fun setCurrentProductList(products: List<Product>?) =
        setDataToPrefs(ConfigKey.PRODUCT, products)

    fun clearAll() {
        cachedData.clear()
        PreferencesHelper.clearAllData()
    }

    fun isLoggedIn(): Boolean = getCurrentUser() != null
}
