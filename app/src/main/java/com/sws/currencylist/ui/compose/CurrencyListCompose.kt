package com.sws.currencylist.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sws.currencylist.R
import com.sws.currencylist.ui.model.Action
import com.sws.currencylist.ui.model.Currency
import com.sws.currencylist.ui.model.UiState
import com.sws.currencylist.ui.viewmodel.CurrencyListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurrencyListScreen(viewModel: CurrencyListViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CurrencyListContent(
        uiState = uiState,
        onSearch = { viewModel.dispatch(Action.Search(it)) }
    )
}

@Composable
fun CurrencyListContent(uiState: UiState, onSearch: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header(query = uiState.query, onSearch = onSearch)
        CurrencyListView(currencies = uiState.currencies)
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, query: String, onSearch: (String) -> Unit) {
    Row(modifier = modifier
        .background(Color.LightGray)
        .padding(end = 8.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchField(modifier = Modifier.weight(1f), query = query, onSearch = onSearch)
        SearchFieldTrailingIcon(isEmptyQuery = query.isEmpty(), onClear = { onSearch("") })
    }
}

@Composable
fun SearchFieldTrailingIcon(modifier: Modifier = Modifier, isEmptyQuery: Boolean, onClear: () -> Unit) {
    val iconModifier = modifier
        .size(32.dp)
        .also {
            if (!isEmptyQuery) { it.clickable { onClear() } }
        }
    val imageVector = if (isEmptyQuery) {
        ImageVector.vectorResource(id = R.drawable.baseline_boy_24)
    } else {
        ImageVector.vectorResource(id = R.drawable.baseline_close_24)
    }

    Icon(
        modifier = iconModifier,
        imageVector = imageVector,
        tint = Color.Gray,
        contentDescription = null
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(modifier: Modifier = Modifier, query: String, onSearch: (String) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        modifier = modifier,
        value = query,
        onValueChange = onSearch,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.DarkGray,
            fontSize = 18.sp
        ),
        decorationBox = {
            TextFieldDefaults.DecorationBox(
                value = query,
                enabled = true,
                innerTextField = it,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(16.dp),
                placeholder = {
                    Text(text = stringResource(id = R.string.search_field_hint),
                        color = Color.Gray)
                },
                container = {}
            )
        }
    )
}

@Composable
fun CurrencyListView(modifier: Modifier = Modifier, currencies: List<Currency>) {
    if (currencies.isEmpty()) {
        EmptyListView(modifier = modifier.fillMaxWidth())
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth()
        ) {
            items(
                count = currencies.size,
                key = { currencies[it].id },
                itemContent = {
                    CurrencyListItem(currency = currencies[it])
                }
            )
        }
    }
}

@Composable
fun EmptyListView(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(modifier = Modifier.size(48.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_accessible_forward_24),
            tint = Color.Gray,
            contentDescription = null)
        Text(text = stringResource(id = R.string.empty_list_message))
    }
}

@Composable
fun CurrencyListItem(modifier: Modifier = Modifier, currency: Currency) {
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray)
                .padding(horizontal = 8.dp, vertical = 4.dp),
                text = currency.name.trim().first().toString().uppercase(),
                fontSize = 18.sp,
                color = Color.White)
        }

        Column(modifier = Modifier) {
            Row(modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    text = currency.name)

                Text(modifier = Modifier.padding(start = 8.dp),
                    text = currency.symbol,
                    fontSize = 18.sp,
                    color = Color.Gray)

                Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Icon(modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                        tint = Color.Gray,
                        contentDescription = null)
                }
            }
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }
    }
}


@Preview
@Composable
fun PreviewCurrencyListContent() {
    val currencies = listOf(
        Currency("id1", "fsdfds sdafdas ", "sadfa"),
        Currency("id2", "fsdfds sdafdas 1", "sadfa1"),
        Currency("id3", "fsdfds sdafdas 2", "sadfa2"),
    )
    val query = "213"
    val uiState = UiState(
        query = query,
        currencies = currencies
    )
    CurrencyListContent(uiState = uiState, onSearch = {})
}