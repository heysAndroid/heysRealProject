package com.hey.heys.model.network.response

import com.google.gson.annotations.SerializedName
import com.hey.heys.model.network.Notification
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
   @SerialName("message") val message: String,
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerializedName("data") val data: List<Notification>,
) : java.io.Serializable