package com.sws.currencylist.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.sws.currencylist.domain.model.CurrencyInfo
import com.sws.currencylist.domain.usecase.AddCurrenciesUseCase
import com.sws.currencylist.domain.usecase.ClearAllCurrenciesUseCase
import com.sws.currencylist.domain.usecase.GetAllCurrenciesUseCase
import com.sws.currencylist.domain.usecase.QueryCurrencyUseCase
import com.sws.currencylist.ui.model.Action
import com.sws.currencylist.ui.model.Currency
import com.sws.currencylist.ui.model.FilterOption
import com.sws.currencylist.ui.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CurrencyListViewModelImpl(
    private val getAllCurrencies: GetAllCurrenciesUseCase,
    private val queryCurrency: QueryCurrencyUseCase,
    private val addCurrencies: AddCurrenciesUseCase,
    private val clearAllCurrencies: ClearAllCurrenciesUseCase
) : CurrencyListViewModel() {
    private val availableCurrencies = MutableStateFlow<List<CurrencyInfo>>(emptyList())
    private val query = MutableStateFlow("")
    private val filter = MutableStateFlow(FilterOption.None)

    private val filteredCurrencies = combine(availableCurrencies, filter) { currencies, filterOpt ->
        when (filterOpt) {
            FilterOption.CryptoOnly -> currencies.filter { it.code == null }
            FilterOption.FiatOnly -> currencies.filter { it.code != null }
            FilterOption.None -> currencies
        }
    }

    override val uiState: StateFlow<UiState> = combine(filteredCurrencies, query) { currencies, query ->
        val resultantCurrencies = queryCurrency.invoke(
            currencies = currencies,
            query = query
        ).map { it.toCurrency() }

        UiState(
            query = query,
            currencies = resultantCurrencies
        )
    }.stateIn(scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = UiState())

    init {
        refresh()
    }

    override fun dispatch(action: Action) {
        when (action) {
            is Action.Search -> query.tryEmit(action.query)
            is Action.Filter -> filter.tryEmit(action.filterOption)
            Action.ClearCurrencies -> clearCurrencies()
            is Action.InsertCurrencies -> insertCurrencies(action.currencies)
        }
    }

    private fun clearCurrencies() {
        viewModelScope.launch {
            clearAllCurrencies.invoke()
            refresh()
        }
    }

    private fun insertCurrencies(currencies: List<CurrencyInfo>) {
        viewModelScope.launch {
            addCurrencies.invoke(currencies)
            refresh()
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            availableCurrencies.value = getAllCurrencies.invoke()
        }
    }

    private fun CurrencyInfo.toCurrency(): Currency {
        return Currency(id = this.id, name = this.name, symbol = this.symbol)
    }
}