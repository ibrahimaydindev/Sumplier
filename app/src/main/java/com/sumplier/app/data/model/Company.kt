package com.sumplier.app.data.model

data class Company(

    val id: Long,
    val companyCode: Long,
    val companyName: String,
    val isActive: Boolean?,
    val resellerCode: Long,
    val email: String,
    val password: String,
    val image: String?

)
