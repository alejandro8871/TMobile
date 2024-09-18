package com.moody.t_mobile.sealed

import com.moody.t_mobile.model.Page

sealed class TMobileUiState {
    object Loading : TMobileUiState()
    data class Success(val page: Page) : TMobileUiState()
    data class Error(val exception: Throwable) : TMobileUiState()
}