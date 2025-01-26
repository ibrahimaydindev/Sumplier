package com.sumplier.app.app

import com.sumplier.app.data.database.PreferencesHelper
import com.sumplier.app.data.enums.ConfigKey
import com.sumplier.app.data.model.Company
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.User

class Config private constructor() {

    private val cachedData = mutableMapOf<ConfigKey, Any?>()
    var user:User? = null
    var company:Company? = null

    companion object {
        @Volatile
        private var instance: Config? = null

        fun getInstance(): Config {
            return instance ?: synchronized(this) {
                instance ?: Config().also { instance = it }
            }
        }
    }

    fun getCurrentUser(): User? {

        if (user != null)
            return user

        return PreferencesHelper.getData(ConfigKey.USER, User::class.java)
    }

    fun getCurrentCompany(): Company? {

        if (company != null)
            return company

        return PreferencesHelper.getData(ConfigKey.COMPANY, Company::class.java)
    }

    fun getCompanyAccounts(): List<CompanyAccount>? {

        return PreferencesHelper.getListData(ConfigKey.COMPANY_ACCOUNT, CompanyAccount::class.java)
    }

    fun clearAll() {
        PreferencesHelper.clearAllData()
    }

    fun isLoggedIn(): Boolean = getCurrentUser() != null
}
