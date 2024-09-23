package com.moody.t_mobile.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.moody.t_mobile.model.Page
import com.moody.t_mobile.repository.TMobileRepository
import com.moody.t_mobile.sealed.TMobileUiState
import com.moody.t_mobile.storage.readUserName
import com.moody.t_mobile.storage.saveUserName
import com.moody.t_mobile.util.isInternetAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMobileViewModel @Inject constructor(
    private val tMobileRepository: TMobileRepository,
    private val context: Application
) : AndroidViewModel(context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val _itemsData = MutableLiveData<TMobileUiState>()
    val itemsData: LiveData<TMobileUiState> = _itemsData

    init {
        if (checkInternetConnection(context)) {
            fetchFeedList()
        } else {
            viewModelScope.launch {
                readUserName(context.dataStore)?.let {
                    _itemsData.value = TMobileUiState.Success(
                        Gson().fromJson(it, Page::class.java)
                    )
                }
            }
        }
    }

    fun fetchFeedList() {
        viewModelScope.launch {
            try {
                _itemsData.value = TMobileUiState.Loading

                val list = tMobileRepository.getItemsFeed()
                saveUserName(context.dataStore, Gson().toJson(list))
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