package com.sugarspoon.pocketstocks

import android.app.Application
import com.instacart.library.truetime.TrueTime
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Apenas para inicializar o hilt
 */
@HiltAndroidApp
class PocketStocksApp: Application()