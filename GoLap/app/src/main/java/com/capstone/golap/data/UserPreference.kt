package com.capstone.golap.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    // Menyimpan session pengguna
    suspend fun saveSession(user: UserModel) {
        if (user.token.isNullOrEmpty()) {
            throw IllegalArgumentException("Token cannot be empty")
        } else {
            Log.d("UserModel", "Token is valid: ${user.token}")
        }

        // Menyimpan data pengguna di DataStore
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            val email = preferences[EMAIL_KEY] ?: ""
            val token = preferences[TOKEN_KEY] ?: ""
            Log.d("UserPreference", "Fetched token: $token")  // Memastikan token diambil dengan benar
            UserModel(email, token, isLogin = preferences[IS_LOGIN_KEY] ?: false)
        }
    }

    // Logout dan menghapus session
    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        // Kunci untuk menyimpan data di DataStore
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        // Menginisialisasi instance UserPreference
        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}