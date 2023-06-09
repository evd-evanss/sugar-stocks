package com.sugarspoon.data.repositories

import com.sugarspoon.data.request.FoundsRequest
import com.sugarspoon.data.response.CredentialsResponse
import com.sugarspoon.data.response.FoundsResponse
import com.sugarspoon.data.response.DetailsItem
import kotlinx.coroutines.flow.Flow

interface FoundsRepository {
    fun getCredentials(): Flow<CredentialsResponse>
    fun getFounds(request: FoundsRequest): Flow<List<FoundsResponse>>
    fun getFundsDetail(code: Int): Flow<DetailsItem>
}