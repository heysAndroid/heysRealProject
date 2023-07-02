package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.network.ChannelFollower
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelFollowerListResponse(
   @SerializedName("data") val channelFollower: ArrayList<ChannelFollower>,
   @SerialName("message") val message: String
) : java.io.Serializable
