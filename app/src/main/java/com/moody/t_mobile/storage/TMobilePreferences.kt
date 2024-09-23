package com.moody.t_mobile.storage

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first


suspend fun saveUserName(dataStore: DataStore<Preferences>, name: String) {
    val userNameKey = stringPreferencesKey("storage_data")
    dataStore.edit { preferences ->
        preferences[userNameKey] = name
    }
}

suspend fun readUserName(dataStore: DataStore<Preferences>): String? {
    val userNameKey = stringPreferencesKey("storage_data")
    return dataStore.data.first()[userNameKey]
}