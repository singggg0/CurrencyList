package com.sws.currencylist.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.sws.currencylist.databinding.ActivityDemoBinding
import com.sws.currencylist.domain.model.CurrencyInfo
import com.sws.currencylist.ui.model.Action
import com.sws.currencylist.ui.viewmodel.CurrencyListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private typealias FilterOption = com.sws.currencylist.ui.model.FilterOption

class DemoActivity : FragmentActivity() {
    private val binding: ActivityDemoBinding by lazy { ActivityDemoBinding.inflate(layoutInflater) }
    private val viewModel: CurrencyListViewModel by viewModel()
    private val sampleData: List<CurrencyInfo> by lazy {
        SampleData.getCryptoCurrencies() + SampleData.getFiatCurrencies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        with(binding) {
            btnClearDb.setOnClickListener {
                viewModel.dispatch(Action.ClearCurrencies)
            }
            btnInsert.setOnClickListener {
                viewModel.dispatch(Action.InsertCurrencies(sampleData))
            }
            btnCrypto.setOnClickListener {
                viewModel.dispatch(Action.Filter(FilterOption.CryptoOnly))
            }
            btnFiat.setOnClickListener {
                viewModel.dispatch(Action.Filter(FilterOption.FiatOnly))
            }
            btnClearFilter.setOnClickListener {
                viewModel.dispatch(Action.Filter(FilterOption.None))
            }
        }
    }
}