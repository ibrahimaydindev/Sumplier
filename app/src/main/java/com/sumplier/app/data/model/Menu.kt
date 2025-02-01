package com.sumplier.app.data.model

data class Menu(
    val id: Long,
    val companyCode: Long,
    val resellerCode: Long,
    val menuCode: Long,
    val menuName: String?,
    val isActive: Boolean

)