package com.sugarspoon.data.response

import com.google.gson.annotations.SerializedName

data class CredentialsResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Int
)