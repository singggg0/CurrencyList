package com.sws.currencylist.domain.usecase.impl

import com.sws.currencylist.domain.model.CurrencyInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class QueryCurrencyUseCaseImplTest {
    private val queryUseCase = QueryCurrencyUseCaseImpl()

    @Test
    fun `should return coins where its name starts with the search term`() {
        val currencies = listOf(
            CurrencyInfo("FoobarId", "Foobar", "Foobar", null),
            CurrencyInfo("BarfooId", "Barfoo", "Barfoo", null),
        )
        val searchTerm = "foo"
        val expected = listOf(CurrencyInfo("FoobarId", "Foobar", "Foobar", null))
        val actual = queryUseCase.invoke(currencies, searchTerm)
        assertEquals(expected, actual)
    }

    @Test
    fun `should return coins where its name contains a partial match with a space prefixed to the search term`() {
        val currencies = listOf(
            CurrencyInfo("ETC", "Ethereum Classic", "ETC", null),
            CurrencyInfo("TRXC", "Tronclassic", "TRXC", null),
        )
        val searchTerm = "Classic"
        val expected = listOf(CurrencyInfo("ETC", "Ethereum Classic", "ETC", null))
        val actual = queryUseCase.invoke(currencies, searchTerm)
        assertEquals(expected, actual)
    }

    @Test
    fun `should return coins where its symbol starts with the search term`() {
        val currencies = listOf(
            CurrencyInfo("ETC", "Ethereum Classic", "ETC", null),
            CurrencyInfo("ETH", "Ethereum", "ETH", null),
            CurrencyInfo("ETN", "Electroneum", "ETN", null),
            CurrencyInfo("BET", "BET name", "BET", null),
        )
        val searchTerm = "ET"
        val expected = listOf(
            CurrencyInfo("ETC", "Ethereum Classic", "ETC", null),
            CurrencyInfo("ETH", "Ethereum", "ETH", null),
            CurrencyInfo("ETN", "Electroneum", "ETN", null)
        )
        val actual = queryUseCase.invoke(currencies, searchTerm)
        assertEquals(expected, actual)
    }
}