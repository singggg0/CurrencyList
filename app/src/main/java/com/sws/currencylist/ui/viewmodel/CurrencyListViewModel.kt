package com.sws.currencylist.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sws.currencylist.ui.model.Action
import com.sws.currencylist.ui.model.UiState
import kotlinx.coroutines.flow.StateFlow

abstract class CurrencyListViewModel : ViewModel() {
    abstract val uiState: StateFlow<UiState>
    abstract fun dispatch(action: Action)
}
