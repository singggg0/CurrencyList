package com.sws.currencylist.domain.repo

import com.sws.currencylist.domain.model.CurrencyInfo

interface CurrencyRepo {
    suspend fun getAllCurrencies(): List<CurrencyInfo>
    suspend fun addCurrencies(currencies: List<CurrencyInfo>)
    suspend fun clearCurrencies()
}