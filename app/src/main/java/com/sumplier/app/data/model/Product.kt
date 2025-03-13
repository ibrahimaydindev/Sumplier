package com.sumplier.app.data.model

class Product(
    val id: Long,
    val categoryCode: Long,
    var resellerCode:Long,
    val productCode: Long,
    val productName: String,
    val price: Double,
    val price2: Double,
    val price3: Double,
    val taxRate: Double,
    val taxRate2: Double,
    val taxRate3: Double,
    val image: String?,
    val description: String?,
    val isActive: Boolean,
    val companyCode: Long,
)
