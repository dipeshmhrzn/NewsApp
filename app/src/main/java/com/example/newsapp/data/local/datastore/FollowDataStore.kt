package com.example.newsapp.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FollowDataStore(
    private val context: Context
) {

    companion object {
        private val Context.dataStore by preferencesDataStore("follow_preferences")
        private val SOURCE_ID = stringSetPreferencesKey("source_id")
    }

    fun getSourceId(): Flow<Set<String>> {
        return context.dataStore.data.map { preferences ->
            preferences[SOURCE_ID] ?: emptySet()
        }
    }

    suspend fun setSourceId(sourceId: String) {
        context.dataStore.edit { preferences ->
            val currentSourceId = preferences[SOURCE_ID] ?: emptySet()
            preferences[SOURCE_ID] = currentSourceId + sourceId
        }
    }

    suspend fun removeSourceId(sourceId: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[SOURCE_ID] ?: emptySet()
            preferences[SOURCE_ID] = current - sourceId
        }
    }

}