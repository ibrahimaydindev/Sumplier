package com.sumplier.app.app

import com.google.gson.reflect.TypeToken
import com.sumplier.app.data.DataStorage
import com.sumplier.app.enums.ConfigKey
import com.sumplier.app.model.Category
import com.sumplier.app.model.CompanyAccount
import com.sumplier.app.model.CompanyDevice
import com.sumplier.app.model.CompanyLicence
import com.sumplier.app.model.ProblemDetails
import com.sumplier.app.model.Product
import com.sumplier.app.model.Ticket
import com.sumplier.app.model.TicketOrder
import com.sumplier.app.model.User

class Config private constructor() {
    private var currentUser: User? = null
    private var currentCategory: Category? = null
    private var currentCompanyAccount: CompanyAccount? = null
    private var currentCompanyDevice: CompanyDevice? = null
    private var currentCompanyLicence: CompanyLicence? = null
    private var currentProblemDetails: ProblemDetails? = null
    private var currentProduct: Product? = null
    private var currentTicket: Ticket? = null
    private var currentTicketOrder: TicketOrder? = null
    private var currentProductList: List<Product>? = null

    companion object {
        @Volatile
        private var instance: Config? = null

        fun getInstance(): Config {
            return instance ?: synchronized(this) {
                instance ?: Config().also { instance = it }
            }
        }
    }

    // User için getter ve setter
    fun getCurrentUser(): User? {
        if (currentUser == null) {
            currentUser = DataStorage.getData(ConfigKey.USER, User::class.java)
        }
        return currentUser
    }

    fun setCurrentUser(user: User?) {
        currentUser = user
        DataStorage.saveData(ConfigKey.USER, user)
    }

    // Category için getter ve setter
    fun getCurrentCategory(): Category? {
        if (currentCategory == null) {
            currentCategory = DataStorage.getData(ConfigKey.CATEGORY, Category::class.java)
        }
        return currentCategory
    }

    fun setCurrentCategory(category: Category?) {
        currentCategory = category
        DataStorage.saveData(ConfigKey.CATEGORY, category)
    }

    fun getCurrentProductList(): List<Product>? {
        if (currentProductList == null) {
            currentProductList = DataStorage.getListData(ConfigKey.PRODUCT, Product::class.java)
        }
        return currentProductList
    }

    fun setCurrentProductList(products: List<Product>?) {
        currentProductList = products
        DataStorage.saveData(ConfigKey.PRODUCT, products)
    }

    // Yardımcı fonksiyonlar
    fun clearAll() {
        currentUser = null
        currentCategory = null
        currentCompanyAccount = null
        currentCompanyDevice = null
        currentCompanyLicence = null
        currentProblemDetails = null
        currentProduct = null
        currentTicket = null
        currentTicketOrder = null
        currentProductList = null
        DataStorage.clearAllData()
    }

    fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }
}