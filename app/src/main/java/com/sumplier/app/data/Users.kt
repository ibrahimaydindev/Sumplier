package com.sumplier.app.data

data class Users(
    private val id: Int,
    private val name: String?,
    private val surname: String?,
    private val email: String?,
    private val password: String?,
    private val loginType: Int?,
    private val isActive: Boolean?,
    private val companyCode: Int?,
)