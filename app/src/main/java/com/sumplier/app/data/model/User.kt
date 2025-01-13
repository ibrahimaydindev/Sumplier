package com.sumplier.app.data.model

data class User(
    val id: Int,
    val name: String?,
    val surname: String?,
    val email: String?,
    val password: String?,
    val loginType: Int?,
    val isActive: Boolean?,
    val companyCode: Int?,
)