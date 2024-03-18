package com.sws.currencylist.ui.viewmodel

import app.cash.turbine.test
import com.sws.currencylist.MainCoroutineRule
import com.sws.currencylist.domain.model.CurrencyInfo
import com.sws.currencylist.domain.usecase.AddCurrenciesUseCase
import com.sws.currencylist.domain.usecase.ClearAllCurrenciesUseCase
import com.sws.currencylist.domain.usecase.GetAllCurrenciesUseCase
import com.sws.currencylist.domain.usecase.impl.QueryCurrencyUseCaseImpl
import com.sws.currencylist.ui.model.Action
import com.sws.currencylist.ui.model.Currency
import com.sws.currencylist.ui.model.FilterOption
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyListViewModelImplTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockGetAllCurrenciesUseCase: GetAllCurrenciesUseCase

    @MockK
    private lateinit var mockAddCurrenciesUseCase: AddCurrenciesUseCase

    @MockK
    private lateinit var mockClearAllCurrenciesUseCase: ClearAllCurrenciesUseCase

    private val fakeCurrencies = listOf(
        CurrencyInfo("BTC", "Bitcoin", "BTC", null),
        CurrencyInfo("HKD", "Hong Kong Dollar", "$", "HKD"),
    )

    private lateinit var viewModel: CurrencyListViewModelImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { mockAddCurrenciesUseCase.invoke(any()) } just runs
        coEvery { mockClearAllCurrenciesUseCase.invoke() } just runs
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun initViewModel() {
        viewModel = CurrencyListViewModelImpl(
            mockGetAllCurrenciesUseCase,
            QueryCurrencyUseCaseImpl(),
            mockAddCurrenciesUseCase,
            mockClearAllCurrenciesUseCase
        )
    }

    @Test
    fun `when filter option is crypto only, should show crypto only`() {
        coEvery { mockGetAllCurrenciesUseCase.invoke() } returns fakeCurrencies
        initViewModel()

        runTest {
            viewModel.dispatch(Action.Filter(FilterOption.CryptoOnly))
            val expected = listOf(Currency("BTC", "Bitcoin", "BTC"))
            viewModel.uiState.test {
                val actual = awaitItem().currencies
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `when filter option is fiat only, should show fiat only`() {
        coEvery { mockGetAllCurrenciesUseCase.invoke() } returns fakeCurrencies
        initViewModel()

        runTest {
            viewModel.dispatch(Action.Filter(FilterOption.FiatOnly))
            val expected = listOf(Currency("HKD", "Hong Kong Dollar", "$"))

            viewModel.uiState.test {
                val actual = awaitItem().currencies
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `when no filtering, should show all currencies`() {
        coEvery { mockGetAllCurrenciesUseCase.invoke() } returns fakeCurrencies
        initViewModel()

        runTest {
            viewModel.dispatch(Action.Filter(FilterOption.None))
            val expected = listOf(
                Currency("BTC", "Bitcoin", "BTC"),
                Currency("HKD", "Hong Kong Dollar", "$")
            )

            viewModel.uiState.test {
                val actual = awaitItem().currencies
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `when search terms match, should show matched currencies`() {
        coEvery { mockGetAllCurrenciesUseCase.invoke() } returns fakeCurrencies
        initViewModel()

        runTest {
            viewModel.dispatch(Action.Search("BT"))
            val expected = listOf(
                Currency("BTC", "Bitcoin", "BTC")
            )

            viewModel.uiState.test {
                val actual = awaitItem().currencies
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `when clear local currencies, should show empty`() {
        coEvery { mockGetAllCurrenciesUseCase.invoke() } returns emptyList()
        initViewModel()

        runTest {
            viewModel.dispatch(Action.ClearCurrencies)
            val expected = emptyList<Currency>()

            viewModel.uiState.test {
                val actual = awaitItem().currencies
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `when insert new currencies, should show updated currencies`() {
        val newCurrencies = listOf(CurrencyInfo("ETC", "Ethereum Classic", "ETC", null))
        val updatedCurrencies = fakeCurrencies + newCurrencies
        coEvery { mockGetAllCurrenciesUseCase.invoke() } returnsMany listOf(fakeCurrencies, updatedCurrencies)
        initViewModel()

        runTest {
            viewModel.dispatch(Action.InsertCurrencies(newCurrencies))
            val expected = listOf(
                Currency("BTC", "Bitcoin", "BTC"),
                Currency("HKD", "Hong Kong Dollar", "$"),
                Currency("ETC", "Ethereum Classic", "ETC"),
            )

            viewModel.uiState.test {
                val actual = awaitItem().currencies
                assertEquals(expected, actual)
            }
        }
    }
}