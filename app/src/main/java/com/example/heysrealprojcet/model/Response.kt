package com.example.heysrealprojcet.model

import kotlinx.serialization.SerialName

data class Response(
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerialName("fromCache") val fromCache: Boolean
):java.io.Serializable
