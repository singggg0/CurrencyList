package com.sws.currencylist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.sws.currencylist.ui.compose.CurrencyListScreen
import com.sws.currencylist.ui.viewmodel.CurrencyListViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CurrencyListFragment : Fragment() {
    private val viewModel: CurrencyListViewModel by activityViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CurrencyListScreen(viewModel = viewModel)
            }
        }
    }
}