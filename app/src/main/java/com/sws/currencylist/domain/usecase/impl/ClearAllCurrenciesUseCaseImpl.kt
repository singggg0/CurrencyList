package com.sws.currencylist.domain.usecase.impl

import com.sws.currencylist.domain.repo.CurrencyRepo
import com.sws.currencylist.domain.usecase.ClearAllCurrenciesUseCase

class ClearAllCurrenciesUseCaseImpl(
    private val repo: CurrencyRepo
) : ClearAllCurrenciesUseCase {
    override suspend fun invoke() {
        repo.clearCurrencies()
    }
}