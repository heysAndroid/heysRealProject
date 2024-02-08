package com.hey.heys.model.network.response

import com.hey.heys.model.network.ChannelFollower
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelFollowerListResponse(
   @SerializedName("data") val channelFollower: ArrayList<ChannelFollower>,
   @SerialName("message") val message: String
) : java.io.Serializable
