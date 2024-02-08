package com.hey.heys.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notification(
   @SerialName("channelId") val channelId: Int,
   @SerialName("content") val content: String,
   @SerialName("createdAt") val createdAt: String,
   @SerialName("isRead") val isRead: Boolean,
   @SerialName("title") val title: String
) : java.io.Serializable