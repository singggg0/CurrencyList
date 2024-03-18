package com.sws.currencylist.ui.model

import com.sws.currencylist.ui.model.Currency

data class UiState(
    val query: String = "",
    val currencies: List<Currency> = emptyList()
)