package com.sumplier.app.data

data class Product(
    private val id: Int?,
    private val categoryCode: String?,
    private val productCode: String?,
    private val productName: String?,
    private val price: Double?,
    private val price2: Double?,
    private val price3: Double?,
    private val taxRate: Double?,
    private val taxRate2: Double?,
    private val taxRate3: Double?,
    private val image: String?,
    private val description: String?,
    private val isActive: Boolean?,
    private val companyCode: String?,
)
