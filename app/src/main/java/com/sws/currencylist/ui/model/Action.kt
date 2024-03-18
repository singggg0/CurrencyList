package com.sws.currencylist.ui.model

import com.sws.currencylist.domain.model.CurrencyInfo

sealed interface Action {
    data class Search(val query: String) : Action
    data class Filter(val filterOption: FilterOption) : Action
    data class InsertCurrencies(val currencies: List<CurrencyInfo>) : Action
    data object ClearCurrencies : Action
}