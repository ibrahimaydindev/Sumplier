package com.sumplier.app.data.model

data class CompanyAccount(

    val id: Long,
    var accountCode: Long,
    val accountName: String,
    val address: String?,
    val region: String?,
    val city: String?,
    var taxNumber:String?,
    var taxOffice:String?,
    val isActive: Boolean,
    val companyCode: Long,
    var resellerCode: Long,
    var phoneNumber: String?

)
