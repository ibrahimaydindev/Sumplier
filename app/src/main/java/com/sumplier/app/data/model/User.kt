package com.sumplier.app.data.model

data class User(
    val id: Long,
    val name: String?,
    val surname: String?,
    val email: String?,
    val password: String?,
    val loginType: Int?,
    val isActive: Boolean?,
    val companyCode: Long,
    val roleCode: String?,
    val resellerCode: Long,
)