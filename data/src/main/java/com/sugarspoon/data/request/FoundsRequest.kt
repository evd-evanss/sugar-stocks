package com.sugarspoon.data.request

import retrofit2.http.Query

data class FoundsRequest(
    @Query("page")
    val page: Int = 1,
    @Query("size")
    val size: Int = 100
)