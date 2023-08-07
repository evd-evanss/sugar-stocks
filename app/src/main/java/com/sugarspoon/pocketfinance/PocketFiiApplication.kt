package com.sugarspoon.pocketfinance

import android.app.Application
import com.instacart.library.truetime.TrueTime
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltAndroidApp
class PocketFiiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initNtpServer()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initNtpServer() {
        GlobalScope.launch(Dispatchers.IO) {
            TrueTime.build()
                .withNtpHost("time.google.com")
                .withSharedPreferencesCache(this@PocketFiiApplication)
                .initialize()
        }
    }
}