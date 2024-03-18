package com.sws.currencylist.data.source

import com.sws.currencylist.domain.model.CurrencyInfo

interface CurrencyLocalDataSource {
    suspend fun getCurrencies(): List<CurrencyInfo>
    suspend fun addCurrencies(currencies: List<CurrencyInfo>)
    suspend fun clear()
}