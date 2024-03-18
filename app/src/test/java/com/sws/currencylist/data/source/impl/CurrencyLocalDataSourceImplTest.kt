package com.sws.currencylist.data.source.impl

import com.sws.currencylist.data.database.dao.CurrencyInfoDao
import com.sws.currencylist.data.database.entity.CurrencyInfoEntity
import com.sws.currencylist.domain.model.CurrencyInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyLocalDataSourceImplTest {
    @MockK
    private lateinit var mockCurrencyInfoDao: CurrencyInfoDao

    private val fakeCurrencyEntities = listOf(
        CurrencyInfoEntity("BTC", "Bitcoin", "BTC", null),
        CurrencyInfoEntity("HKD", "Hong Kong Dollar", "$", "HKD"),
    )

    private lateinit var dataSource: CurrencyLocalDataSourceImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { mockCurrencyInfoDao.getAll() } returns fakeCurrencyEntities

        dataSource = CurrencyLocalDataSourceImpl(mockCurrencyInfoDao, Dispatchers.IO)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should return all currencies`() {
        runTest {
            val expected = listOf(
                CurrencyInfo("BTC", "Bitcoin", "BTC", null),
                CurrencyInfo("HKD", "Hong Kong Dollar", "$", "HKD"),
            )
            val actual = dataSource.getCurrencies()
            assertEquals(expected, actual)
        }
    }
}