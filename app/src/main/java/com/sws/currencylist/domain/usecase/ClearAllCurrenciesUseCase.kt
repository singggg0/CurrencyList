package com.sws.currencylist.domain.usecase

interface ClearAllCurrenciesUseCase {
    suspend operator fun invoke()
}