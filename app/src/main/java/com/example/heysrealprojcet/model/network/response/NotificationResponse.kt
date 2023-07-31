package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.network.Notification
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
   @SerialName("message") val message: String,
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerializedName("data") val data: List<Notification>,
) : java.io.Serializable