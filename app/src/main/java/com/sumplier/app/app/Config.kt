package com.sumplier.app.app

import android.util.LongSparseArray
import com.sumplier.app.data.database.PreferencesHelper
import com.sumplier.app.data.enums.ConfigKey
import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.Company
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.Menu
import com.sumplier.app.data.model.Product
import com.sumplier.app.data.model.User

class Config private constructor() {

    lateinit var user: User
    lateinit var company: Company
    val productMap: LongSparseArray<Product> = LongSparseArray()
    val menuMap: LongSparseArray<Menu> = LongSparseArray()
    val categoryMap: LongSparseArray<Category> = LongSparseArray()
    val accountMap : LongSparseArray<CompanyAccount> = LongSparseArray()

    companion object {
        private var instance: Config? = null

        fun getInstance(): Config {
            if (instance == null) {
                instance = Config()
            }
            return instance!!
        }
    }

    fun setCurrentUser(user: User) {
        this.user = user
    }

    fun setCurrentCompany(company: Company) {
        this.company = company
    }
    fun getCurrentUser(): User {
        return user
    }

    fun getCurrentCompany(): Company {
        return company
    }

    fun getCompanyAccounts(): List<CompanyAccount>? {

        return PreferencesHelper.getListData(ConfigKey.COMPANY_ACCOUNT, CompanyAccount::class.java)
    }

    fun clearAll() {
        PreferencesHelper.clearAllData()
    }

    /**
     * Function: checkSetProducts()
     */
    fun checkSetProducts() {

        productMap.clear()

        val products: List<Product> = PreferencesHelper.getListData(ConfigKey.PRODUCT, Product::class.java)
            ?: return

        // Check and load products to RAM database!...
        for (product in products) {

            // Set product to map!...
            productMap.put(product.id, product)
        }
    }

    /**
     * Function: checkSetMenus()
     */
    fun checkSetMenus() {

        menuMap.clear()

        val menus: List<Menu> = PreferencesHelper.getListData(ConfigKey.MENU, Menu::class.java)
            ?: return

        // Check and load menus to RAM database!...
        for (menu in menus) {
            // Set product to map!...
            menuMap.put(menu.id, menu)
        }

    }

    /**
     * Function: checkSetCategories()
     */
    fun checkSetCategories() {

        categoryMap.clear()

        val categories: List<Category> = PreferencesHelper.getListData(ConfigKey.CATEGORY, Category::class.java)
            ?: return

        // Check and load categories to RAM database!...
        for (category in categories) {
            // Set product to map!...
            categoryMap.put(category.id, category)
        }
    }


    fun getMenus(): ArrayList<Menu> {
        val menus = ArrayList<Menu>()

        for (i in 0 until menuMap.size()) {
            val menu = menuMap.valueAt(i)

            menus.add(menu)
        }

        return menus
    }

    /**
     * Function: checkSetMenus()
     */
    fun checkSetAccounts() {

        accountMap.clear()

        val accounts: List<CompanyAccount> = PreferencesHelper.getListData(ConfigKey.COMPANY_ACCOUNT, CompanyAccount::class.java)
            ?: return

        // Check and load menus to RAM database!...
        for (account in accounts) {
            accountMap.put(account.id, account)
        }

    }

    /**
     * Function: getAccountById()
     */
    fun getAccountById(accountId:Long?) :CompanyAccount?  {

        return accountId?.let { accountMap.get(it) }

    }
    fun getAccountByCode(code: Long): CompanyAccount? {

        for (i in 0 until accountMap.size()) {
            val account = accountMap.valueAt(i)
            if (account.accountCode == code) {
                return account
            }
        }
        // Eşleşen bir değer bulunmazsa null döndürülür
        return null
    }



    fun getDefaultMenu():Menu{

        val menus = getMenus()

        for (menu:Menu in menus)
        {
            if (menu.isActive)
                return menu
        }
        return menus[0];
    }
    fun getMenuCategory(menu: Menu): ArrayList<Category> {
        val categories = ArrayList<Category>()

        val menuCode = menu.menuCode

        for (i in 0 until categoryMap.size()) {
            val category = categoryMap.valueAt(i)

            if (category.menuCode == menuCode) {
                categories.add(category)
            }
        }

        return categories
    }

    fun getCategoryProducts(category: Category): ArrayList<Product> {
        val products = ArrayList<Product>()

        val categoryCode = category.categoryCode

        for (i in 0 until productMap.size()) {
            val product = productMap.valueAt(i)

            if (product.categoryCode == categoryCode) {
                products.add(product)
            }
        }

        return products
    }


}
