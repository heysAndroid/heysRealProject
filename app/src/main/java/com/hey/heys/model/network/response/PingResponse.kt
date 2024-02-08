package com.hey.heys.model.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PingResponse(
   @SerialName("ResponseData") val response: String
) : java.io.Serializable