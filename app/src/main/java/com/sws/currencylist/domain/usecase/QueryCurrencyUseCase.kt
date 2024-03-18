package com.sws.currencylist.domain.usecase

import com.sws.currencylist.domain.model.CurrencyInfo

interface QueryCurrencyUseCase {
    operator fun invoke(currencies: List<CurrencyInfo>, query: String): List<CurrencyInfo>
}