package com.sugarspoon.data.repositories

import com.sugarspoon.data.sources.BrapiDataSource
import com.sugarspoon.domain.model.remote.ApiDataDto
import com.sugarspoon.domain.model.remote.MarketStatusDto
import com.sugarspoon.domain.model.remote.QuotesResponseDto
import com.sugarspoon.domain.repositories.ApiRepository
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ApiRepositoryImpl @Inject constructor(
    private val dataSource: BrapiDataSource
) : ApiRepository {

    override suspend fun getApiData(): Result<ApiDataDto> {
        return Result.runCatching {
            withContext(Dispatchers.Default) {
                val result = withContext(Dispatchers.IO) {
                    val responseDTO = async { dataSource.getQuoteList() }
                    val inflationResponseDTO = async { dataSource.getInflation(country = "brazil", historical = false) }
                    val marketStatus = async { getMarketStatus() }
                    val marketLocalData = async { dataSource.getDetails(tickers = "^BVSP") }

                    ApiDataDto(
                        responseDto = responseDTO.await(),
                        inflationResponseDTO = inflationResponseDTO.await(),
                        marketStatusDto = marketStatus.await(),
                        quotesResponseDto = marketLocalData.await()
                    )
                }
                result
            }
        }
    }

    override suspend fun getDetail(
        tickers: String,
        range: String,
        interval: String,
    ): Result<QuotesResponseDto> {
        return Result.runCatching {
            withContext(Dispatchers.Default) {
                val result = withContext(Dispatchers.IO) {
                    dataSource.getDetails(tickers, range, interval)
                }
                result
            }
        }
    }

    private fun getMarketStatus(): MarketStatusDto {
        val currentDate = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("GMT-3") // Brazilian timezone.
        }
        val dayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK)
        val timeOfDay = currentDate.get(Calendar.HOUR_OF_DAY)
        val marketStatus = if(dayOfWeek == Calendar.SUNDAY || dayOfWeek ==Calendar.SATURDAY) {
            MarketStatusDto(message = "Fechada.", false)
        } else {
            when (timeOfDay) {
                in 10..17 -> MarketStatusDto("Aberta para negociações.", true)
                else -> MarketStatusDto("Fechada para negociações.", false)
            }
        }
        return marketStatus
    }
}