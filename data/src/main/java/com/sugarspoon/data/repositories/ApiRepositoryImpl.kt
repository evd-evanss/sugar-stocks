package com.sugarspoon.data.repositories

import com.sugarspoon.data.sources.BrapiDataSource
import com.sugarspoon.domain.model.remote.MarketStatusDto
import com.sugarspoon.domain.repositories.ApiRepository
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
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
            //time = TrueTime.now()
            timeZone = TimeZone.getTimeZone("GMT-3") // Brazilian timezone.
        }
        val dayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK)
        val timeOfDay = currentDate.get(Calendar.HOUR_OF_DAY)

        when (dayOfWeek) {
            Calendar.SUNDAY,
            Calendar.SATURDAY -> {
                emit(MarketStatusDto(message = "Fechada.", false))
                return@flow
            }
        }

        val marketStatus = when (timeOfDay) {
            in 10..17 -> {
                MarketStatusDto("Aberta para negociações.", true)
            }

            else -> {
                MarketStatusDto("Fechada para negociações.", false)
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