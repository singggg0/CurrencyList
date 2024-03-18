package com.sws.currencylist.di

import com.sws.currencylist.data.database.CurrencyDataBase
import com.sws.currencylist.data.repo.CurrencyRepoImpl
import com.sws.currencylist.data.source.CurrencyLocalDataSource
import com.sws.currencylist.data.source.impl.CurrencyLocalDataSourceImpl
import com.sws.currencylist.domain.repo.CurrencyRepo
import com.sws.currencylist.domain.usecase.AddCurrenciesUseCase
import com.sws.currencylist.domain.usecase.ClearAllCurrenciesUseCase
import com.sws.currencylist.domain.usecase.GetAllCurrenciesUseCase
import com.sws.currencylist.domain.usecase.QueryCurrencyUseCase
import com.sws.currencylist.domain.usecase.impl.AddCurrenciesUseCaseImpl
import com.sws.currencylist.domain.usecase.impl.ClearAllCurrenciesUseCaseImpl
import com.sws.currencylist.domain.usecase.impl.GetAllCurrenciesUseCaseImpl
import com.sws.currencylist.domain.usecase.impl.QueryCurrencyUseCaseImpl
import com.sws.currencylist.ui.viewmodel.CurrencyListViewModel
import com.sws.currencylist.ui.viewmodel.CurrencyListViewModelImpl
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val currencyModule = module {
    single<CurrencyDataBase> { CurrencyDataBase.getDatabase(androidContext()) }
    factory<CurrencyLocalDataSource> {
        val dataBase = get<CurrencyDataBase>()
        CurrencyLocalDataSourceImpl(dataBase.currencyInfoDao(), Dispatchers.IO)
    }
    factory<CurrencyRepo> { CurrencyRepoImpl(get()) }
    factory<GetAllCurrenciesUseCase> { GetAllCurrenciesUseCaseImpl(get()) }
    factory<ClearAllCurrenciesUseCase> { ClearAllCurrenciesUseCaseImpl(get()) }
    factory<QueryCurrencyUseCase> { QueryCurrencyUseCaseImpl() }
    factory<AddCurrenciesUseCase> { AddCurrenciesUseCaseImpl(get()) }
    viewModel<CurrencyListViewModel> { CurrencyListViewModelImpl(get(), get(), get(), get()) }
}