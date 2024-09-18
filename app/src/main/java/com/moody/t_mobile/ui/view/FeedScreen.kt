package com.moody.t_mobile.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.moody.t_mobile.sealed.TMobileUiState
import com.moody.t_mobile.viewmodel.TMobileViewModel


@Composable
fun FeedScreen(modifier: Modifier, viewModel: TMobileViewModel = hiltViewModel()) {
    val tMobileUiState by viewModel.itemsData.observeAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        when (tMobileUiState) {
            is TMobileUiState.Success -> {
                val items = (tMobileUiState as TMobileUiState.Success).page
                LazyColumn(Modifier.fillMaxSize()) {
                    items(items.page.cards) { item ->
                        when (item.card_type) {
                            "text" -> TextCard(item)
                            "title_description" -> TitleDescriptionCard(item)
                            "image_title_description" -> ImageTitleDescriptionCard(item)
                        }
                    }
                }
            }

            is TMobileUiState.Loading -> {
                // Show a loading indicator
                Text(
                    "Loading...",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            is TMobileUiState.Error -> {
                // Show an error message
                Text(
                    "Error: ${(tMobileUiState as TMobileUiState.Error).exception.message}",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            else -> {} // Handle initial state or null
        }
    }
}