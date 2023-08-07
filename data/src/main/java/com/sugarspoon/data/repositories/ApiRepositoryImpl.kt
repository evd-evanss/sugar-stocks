package com.sugarspoon.data.repositories

import com.instacart.library.truetime.TrueTime
import com.sugarspoon.data.response.InflationDTO
import com.sugarspoon.data.response.MarketStatus
import com.sugarspoon.data.sources.BrapiDataSource
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiRepositoryImpl @Inject constructor(
    private val dataSource: BrapiDataSource
) : ApiRepository {

    override fun getQuoteList() = flow {
        emit(dataSource.getQuoteList())
    }

    override fun getDetail(
        tickers: String,
        range: String,
        interval: String,
    ) = flow {
        emit(dataSource.getDetails(tickers, range, interval))
    }

    override fun getMarketStatus() = flow {
        val currentDate = Calendar.getInstance().apply {
            time = TrueTime.now()
            timeZone = TimeZone.getTimeZone("GMT-3") // Brazilian timezone.
        }

        when (currentDate.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY,
            Calendar.SATURDAY -> {
                emit(MarketStatus(message = "Fechada.", false))
                return@flow
            }
        }

        val marketStatus = when (currentDate.get(Calendar.HOUR_OF_DAY)) {
            in 10..17 -> {
                MarketStatus("Aberta para negociações.", true)
            }

            else -> {
                MarketStatus("Fechada para negociações.", false)
            }
        }
        emit(marketStatus)
    }

    override fun getInflation(
        country: String,
        historical: Boolean,
        start: String,
        end: String
    ) = flow {
        emit(dataSource.getInflation(country, historical, start, end))
    }
}