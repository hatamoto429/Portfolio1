package com.example.portfolio1.preferences

import kotlinx.coroutines.flow.Flow

interface PreferenceStorage {

    fun savedKey(): Flow<Boolean>
    suspend fun setSavedKey(order: Boolean)

}