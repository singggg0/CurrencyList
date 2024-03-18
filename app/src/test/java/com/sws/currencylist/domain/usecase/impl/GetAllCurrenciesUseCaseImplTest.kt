package com.sws.currencylist.domain.usecase.impl

import com.sws.currencylist.domain.model.CurrencyInfo
import com.sws.currencylist.domain.repo.CurrencyRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllCurrenciesUseCaseImplTest {
    @MockK
    private lateinit var mockCurrencyRepo: CurrencyRepo

    private val fakeCurrencies = listOf(
        CurrencyInfo("BTC", "Bitcoin", "BTC", null),
        CurrencyInfo("HKD", "Hong Kong Dollar", "$", "HKD"),
    )

    private lateinit var useCase: GetAllCurrenciesUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { mockCurrencyRepo.getAllCurrencies() } returns fakeCurrencies
        useCase = GetAllCurrenciesUseCaseImpl(mockCurrencyRepo)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should return all currencies`() {
        runTest {
            val expected = fakeCurrencies
            val actual = useCase.invoke()
            assertEquals(expected, actual)
        }
    }
}