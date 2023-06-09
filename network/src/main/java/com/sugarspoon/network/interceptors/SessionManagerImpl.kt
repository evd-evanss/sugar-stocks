package com.sugarspoon.network.interceptors

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Session manager to save and fetch data from SharedPreferences
 */

private const val USER_TOKEN = "user_token"

class SessionManagerImpl @Inject constructor( @ApplicationContext context: Context): SessionManager {
    
    private var prefs: SharedPreferences = context
        .getSharedPreferences("PocketFinance", Context.MODE_PRIVATE)

    override fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    override fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}

interface SessionManager {
    fun saveAuthToken(token: String)
    fun fetchAuthToken(): String?
}