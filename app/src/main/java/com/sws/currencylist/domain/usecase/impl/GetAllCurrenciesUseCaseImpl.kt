package com.sws.currencylist.domain.usecase.impl

import com.sws.currencylist.domain.model.CurrencyInfo
import com.sws.currencylist.domain.repo.CurrencyRepo
import com.sws.currencylist.domain.usecase.GetAllCurrenciesUseCase

class GetAllCurrenciesUseCaseImpl(
    private val repo: CurrencyRepo
) : GetAllCurrenciesUseCase {
    override suspend fun invoke(): List<CurrencyInfo> {
        return repo.getAllCurrencies()
    }
}