package com.example.heysrealprojcet.model.network.response

import kotlinx.serialization.SerialName

data class LoginResponse(
   @SerialName("token") val token: String,
   @SerialName("message") val message: String,
) : java.io.Serializable
