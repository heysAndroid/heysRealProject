package com.hey.heys.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelLink(
   @SerialName("id") val id: Int,
   @SerialName("link") val link: String,
) : java.io.Serializable