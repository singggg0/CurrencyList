package com.sws.currencylist.domain.usecase.impl

import com.sws.currencylist.domain.model.CurrencyInfo
import com.sws.currencylist.domain.repo.CurrencyRepo
import com.sws.currencylist.domain.usecase.AddCurrenciesUseCase

class AddCurrenciesUseCaseImpl(
    private val repo: CurrencyRepo
) : AddCurrenciesUseCase {
    override suspend fun invoke(currencies: List<CurrencyInfo>) {
        repo.addCurrencies(currencies)
    }
}