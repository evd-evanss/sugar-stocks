package com.sugarspoon.data.repositories

import com.sugarspoon.data.request.CredentialsRequest
import com.sugarspoon.data.sources.FoundsDataSource
import javax.inject.Inject

class FoundsRepositoryImpl @Inject constructor(
    private val dataSource: FoundsDataSource
) : FoundsRepository {

    override fun getCredentials() =
        dataSource.refreshToken(CredentialsRequest())

    override fun getFounds(request: com.sugarspoon.data.request.FoundsRequest) =
        dataSource.getFound(request)

    override fun getFundsDetail(code: Int) = dataSource.getFoundDetails(code)
}