package com.sugarspoon.data.api

import com.sugarspoon.data.request.CredentialsRequest
import com.sugarspoon.data.response.ApiResponse
import com.sugarspoon.data.response.CredentialsResponse
import com.sugarspoon.data.response.Details
import com.sugarspoon.data.response.DetailsItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiFounds {

    @POST("/oauth/access-token")
    suspend fun getCredentials(
        @Body request: CredentialsRequest
    ): CredentialsResponse

    @GET("/feed/fundos/v1/fundos")
    suspend fun getFounds(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ApiResponse

    @GET("/feed/fundos/v1/fundos/{codigo_fundo/notas-explicativas")
    suspend fun getDetails(
        @Query("codigo_fundo") code: Int,
    ): Details

}

data class DetailsResponse(
    val response: List<DetailsItem>
)