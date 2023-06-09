package com.sugarspoon.data.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class FoundsResponse(
    @SerializedName("codigo_fundo")
    val code: String,
    @SerializedName("nome_fantasia")
    val fantasyName: String,
    @SerializedName("cnpj_fundo")
    val cnpj: String,
    @SerializedName("classe_anbima") // Renda fixa RF, Ações, Cambial, Multimercado MM, Previdência
    val type: String,
    @SerializedName("situacao_atual")
    val status: String, //Ativo ou Encerrado
    @SerializedName("data_inicio_divulgacao_cota")
    val initialDate: String, //YYYY-MM-DD,
    @SerializedName("data_atualizacao")
    val lastUpdate: String, //YYYY-MM-DD HH:MI:SS
)