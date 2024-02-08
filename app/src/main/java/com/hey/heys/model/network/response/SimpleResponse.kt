package com.hey.heys.model.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
   @SerialName("message") val message: String
) : java.io.Serializable
