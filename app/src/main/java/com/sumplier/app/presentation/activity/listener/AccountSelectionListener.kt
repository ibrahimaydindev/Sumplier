package com.sumplier.app.presentation.activity.listener

import com.sumplier.app.data.model.CompanyAccount

interface AccountSelectionListener {
    fun onAccountSelected(account: CompanyAccount)
    fun onPopupDismissed()
}