package com.sws.currencylist.data.repo

import com.sws.currencylist.data.source.CurrencyLocalDataSource
import com.sws.currencylist.domain.model.CurrencyInfo
import com.sws.currencylist.domain.repo.CurrencyRepo

class CurrencyRepoImpl(
    private val localDataSource: CurrencyLocalDataSource
) : CurrencyRepo {
    override suspend fun getAllCurrencies(): List<CurrencyInfo> {
        return localDataSource.getCurrencies()
    }

    override suspend fun addCurrencies(currencies: List<CurrencyInfo>) {
        localDataSource.addCurrencies(currencies)
    }

    override suspend fun clearCurrencies() {
        localDataSource.clear()
    }
}