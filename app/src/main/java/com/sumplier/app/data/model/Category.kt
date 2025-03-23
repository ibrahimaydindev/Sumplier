package com.sumplier.app.data.model

data class Category(
     val id: Long,
     val categoryCode: Long,
     val categoryName: String,
     val isActive: Boolean,
     val companyCode: Long,
     val menuCode:Long
)
