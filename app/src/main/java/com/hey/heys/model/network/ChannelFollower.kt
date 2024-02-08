package com.hey.heys.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelFollower(
   @SerialName("id") val id: Int,
   @SerialName("gender") val gender: String,
   @SerialName("username") val username: String,
   @SerialName("requestedAt") val requestedAt: String,
) : java.io.Serializable