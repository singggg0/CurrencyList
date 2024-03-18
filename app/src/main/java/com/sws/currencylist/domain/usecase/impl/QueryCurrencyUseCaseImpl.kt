package com.sws.currencylist.domain.usecase.impl

import com.sws.currencylist.domain.model.CurrencyInfo
import com.sws.currencylist.domain.usecase.QueryCurrencyUseCase

class QueryCurrencyUseCaseImpl : QueryCurrencyUseCase {
    override fun invoke(currencies: List<CurrencyInfo>, query: String): List<CurrencyInfo> {
        return currencies.filter {
            val names = it.name.split(" ")
            val texts = mutableListOf(it.symbol).also { it.addAll(names) }
            texts.any { it.startsWith(prefix = query.trim(), ignoreCase = true) }
        }
    }
}