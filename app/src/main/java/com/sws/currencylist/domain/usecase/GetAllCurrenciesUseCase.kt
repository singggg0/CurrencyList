package com.sws.currencylist.domain.usecase

import com.sws.currencylist.domain.model.CurrencyInfo

interface GetAllCurrenciesUseCase {
    suspend operator fun invoke(): List<CurrencyInfo>
}