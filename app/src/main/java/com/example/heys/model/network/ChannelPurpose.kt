package com.example.heys.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelPurpose(
   @SerialName("id") val id: String,
   @SerialName("purpose") val purpose: String
) : java.io.Serializable
