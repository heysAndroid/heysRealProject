package com.example.heys.model.network.response

import kotlinx.serialization.SerialName

data class SignUpResponse(
   @SerialName("token") val token: String,
   @SerialName("message") val message: String,
) : java.io.Serializable