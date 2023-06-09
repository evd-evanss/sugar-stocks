package com.sugarspoon.data.response

import com.google.gson.annotations.SerializedName

class Details : ArrayList<DetailsItem>()

data class DetailsItem(
    @SerializedName("ano_divulgacao")
    val ano_divulgacao: String,
    @SerializedName("descricao")
    val descricao: String
)