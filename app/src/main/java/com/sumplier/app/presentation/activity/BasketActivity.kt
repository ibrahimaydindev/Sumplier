package com.sumplier.app.presentation.activity

import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.api.managers.TicketApiManager
import com.sumplier.app.data.api.managers.TicketOrderApiManager
import com.sumplier.app.data.model.Category
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.data.model.Product
import com.sumplier.app.data.model.Ticket
import com.sumplier.app.data.model.TicketOrder
import com.sumplier.app.presentation.activity.adapter.CategoryAdapter
import com.sumplier.app.presentation.activity.adapter.ProductAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BasketActivity : AppCompatActivity() {

    private lateinit var currentAccount: CompanyAccount
    private lateinit var menuCategories: List<Category>
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var totalPriceTv: TextView
    private lateinit var totalQuantityTv: TextView


    private var orderCode: Long? = 0

    private var totalPrice : Double = 0.0

    private var currentOrder : TicketOrder? = null;

    private var currentItems: ArrayList<TicketOrder> = ArrayList()

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

        totalPriceTv = findViewById(R.id.totalPriceValue)
        totalQuantityTv = findViewById(R.id.quantityText)

        findViewById<LinearLayout>(R.id.onSendBasket)?.setOnClickListener {

            createSendOrder()
        }


        setupRecyclerView()
        updatePriceAndQuantity()
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
        val productsForCategory = getProductsForCategory(category)
        productAdapter.updateProducts(productsForCategory)
    }

    private fun updatePriceAndQuantity(){
        totalPriceTv.text = totalPrice.toString()
        totalQuantityTv.text = currentItems.size.toString() + " Adet"

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createSendOrder(){

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        val createDateTime = LocalDateTime.now().format(formatter)
        val modifiedDateTime = LocalDateTime.now().format(formatter)

        val ticket = Ticket(
            ticketCode = 0,
            companyCode = Config.getInstance().getCurrentCompany()?.companyCode,
            userCode = 0,
            createDateTime = createDateTime,
            modifiedDateTime = modifiedDateTime,
            total = totalPrice,
            taxTotal = 0.0,
            generalTotal = totalPrice,
            paymentType = "CASH",
            description = "CASH PAID",
            status = 0,
            resellerCode = Config.getInstance().getCurrentCompany()?.resellerCode,
            accountCode = currentAccount.id,  // TODO
            deviceCode = "DEV001"

        )

        val ticketApiManager = TicketApiManager()

        try {
            ticketApiManager.postTicket(ticket) { ticket1 ->

                if (ticket1 != null) {
                    orderCode = ticket1.ticketCode

                    if (orderCode != null)
                        postTicketOrders()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun postTicketOrders() {

        for(item:TicketOrder in currentItems){

            postTicketOrder(item)

        }

    }


    private fun postTicketOrder(ticketOrder: TicketOrder){


        ticketOrder.ticketCode = orderCode
        val ticketOrderApiManager = TicketOrderApiManager()

        try {
            ticketOrderApiManager.postTicketOrder(ticketOrder) { isSuccess ->

                if (isSuccess) {
                    println("Ticket Order post başarılı oldu ")
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setupProductRecyclerView() {
        val recyclerViewProducts = findViewById<RecyclerView>(R.id.recyclerViewProducts)

        //Listen product card clicks...
        productAdapter = ProductAdapter(getProductsForCategory(menuCategories[0])) { product ->

            if (product.price != null)
                totalPrice += product.price

            val clickedItem : Product = product
            val itemTicketOrder = TicketOrder(
                id = 0,
                ticketCode = null,
                productCode = clickedItem.productCode,
                productName = clickedItem.productName,
                quantity = 1.0,
                price = clickedItem.price,
                totalPrice = clickedItem.price?.times(1.0),
                status = 0,
                isChange = false,
                newQuantity = 0.0,
                newPrice = 0.0,
                newTotalPrice = 0.0,
                companyCode = Config.getInstance().getCurrentCompany()?.companyCode,
                deviceCode = "TEST01"

            )

            currentItems.add(itemTicketOrder)

            Toast.makeText(this, "${product.productName} sepete eklendi!", Toast.LENGTH_SHORT).show()
            updatePriceAndQuantity()
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
