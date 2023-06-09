package com.sugarspoon.data.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("total_elements")
    val totalElements: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("content")
    val content: List<FoundsResponse>
)