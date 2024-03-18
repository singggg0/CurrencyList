package com.sws.currencylist.data.source.impl

import com.sws.currencylist.data.database.dao.CurrencyInfoDao
import com.sws.currencylist.data.database.entity.CurrencyInfoEntity
import com.sws.currencylist.data.source.CurrencyLocalDataSource
import com.sws.currencylist.domain.model.CurrencyInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CurrencyLocalDataSourceImpl(
    private val dao: CurrencyInfoDao,
    private val ioDispatcher: CoroutineDispatcher
) : CurrencyLocalDataSource {

    override suspend fun getCurrencies(): List<CurrencyInfo> {
        return withContext(ioDispatcher) {
            dao.getAll().map { it.toCurrencyInfo() }
        }
    }

    override suspend fun addCurrencies(currencies: List<CurrencyInfo>) {
        return withContext(ioDispatcher) {
            val entities = currencies.map { it.toCurrencyInfoEntity() }
            dao.insertAll(*entities.toTypedArray())
        }
    }

    override suspend fun clear() {
        return withContext(ioDispatcher) {
            dao.clear()
        }
    }

    private fun CurrencyInfoEntity.toCurrencyInfo(): CurrencyInfo {
        return CurrencyInfo(id = this.id,
            name = this.name,
            symbol = this.symbol,
            code = this.code)
    }

    private fun CurrencyInfo.toCurrencyInfoEntity(): CurrencyInfoEntity {
        return CurrencyInfoEntity(id = this.id,
            name = this.name,
            symbol = this.symbol,
            code = this.code)
    }
}