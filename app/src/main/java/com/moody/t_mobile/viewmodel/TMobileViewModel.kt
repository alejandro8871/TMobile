package com.moody.t_mobile.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moody.t_mobile.repository.TMobileRepository
import com.moody.t_mobile.sealed.TMobileUiState
import com.moody.t_mobile.util.isInternetAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMobileViewModel @Inject constructor(
    private val tMobileRepository: TMobileRepository,
    private val context: Application
) : AndroidViewModel(context) {

    private val _itemsData = MutableLiveData<TMobileUiState>()
    val itemsData: LiveData<TMobileUiState> = _itemsData

    init {
        if (checkInternetConnection(context)) {
            fetchFeedList()
        } else {

        }
    }

    private fun fetchFeedList() {
        viewModelScope.launch {
            try {
                _itemsData.value = TMobileUiState.Loading

                val list = tMobileRepository.getItemsFeed()
                _itemsData.value = TMobileUiState.Success(list)

            } catch (e: Exception) {
                _itemsData.value = TMobileUiState.Error(e)
            }
        }
    }

    private fun checkInternetConnection(context: Context): Boolean {
        return isInternetAvailable(context)
    }
}