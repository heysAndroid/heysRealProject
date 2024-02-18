package com.hey.heys.model.network.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceTokenResponse(
   @SerialName("message") val message: String,
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerializedName("data") val data: Unit,
) : java.io.Serializable