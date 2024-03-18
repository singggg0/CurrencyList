package com.sws.currencylist.domain.usecase

import com.sws.currencylist.domain.model.CurrencyInfo

interface AddCurrenciesUseCase {
    suspend operator fun invoke(currencies: List<CurrencyInfo>)
}