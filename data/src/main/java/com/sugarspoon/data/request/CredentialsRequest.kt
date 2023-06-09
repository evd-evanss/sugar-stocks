package com.sugarspoon.data.request

import com.google.gson.annotations.SerializedName

data class CredentialsRequest(
    @SerializedName("grant_type")
    val grantType: String = "client_credentials"
)
