package com.example.heysrealprojcet.model

import kotlinx.serialization.SerialName

data class Response(
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerialName("fromCache") val fromCache: Boolean,
   @SerialName("accessToken") val accessToken: String,
   @SerialName("refreshToken") val refreshToken: String
) : java.io.Serializable
