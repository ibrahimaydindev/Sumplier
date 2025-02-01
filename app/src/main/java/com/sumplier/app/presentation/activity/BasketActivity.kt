package com.sumplier.app.presentation.activity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.Menu
import com.sumplier.app.data.model.Product
import com.sumplier.app.presentation.activity.adapter.CategoryAdapter
import com.sumplier.app.presentation.activity.adapter.ProductAdapter

class BasketActivity : AppCompatActivity() {

    private lateinit var currentAccount: CompanyAccount
    private lateinit var menuCategories: List<Category>
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        val accountId = intent.getLongExtra("account_id", -1L)
        if (accountId == -1L) {
            Toast.makeText(this, "Geçersiz Account: $accountId", Toast.LENGTH_SHORT).show()
            return
        }

        currentAccount = Config.getInstance().getAccountById(accountId) ?: return
        menuCategories = Config.getInstance().getMenuCategory(Config.getInstance().getDefaultMenu())

        setupRecyclerView()
    }

    private fun setupCategoryRecyclerView() {
        val recyclerViewCategories = findViewById<RecyclerView>(R.id.recyclerViewCategories)

        categoryAdapter = CategoryAdapter(menuCategories) { category ->
            updateProductList(category)
        }

        recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategories.adapter = categoryAdapter
    }

    private fun updateProductList(category: Category) {
        val productsForCategory = getProductsForCategory(category) // Seçilen kategoriye ait ürünleri getir
        productAdapter.updateProducts(productsForCategory) // Adapter içindeki ürünleri güncelle
    }


    private fun setupProductRecyclerView() {
        val recyclerViewProducts = findViewById<RecyclerView>(R.id.recyclerViewProducts)

        productAdapter = ProductAdapter(getProductsForCategory(menuCategories[0])) { product ->
            Toast.makeText(this, "${product.productName} sepete eklendi!", Toast.LENGTH_SHORT).show()
        }

        recyclerViewProducts.layoutManager = GridLayoutManager(this, 2)
        recyclerViewProducts.adapter = productAdapter
    }

    private fun setupRecyclerView() {
        setupCategoryRecyclerView()
        setupProductRecyclerView()

    }

    private fun getProductsForCategory(category: Category): List<Product> {
        return Config.getInstance().getCategoryProducts(category)
    }
}
