package com.example.heys.model.network.response

import com.example.heys.model.network.ChannelDetail
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelDetailResponse(
   @SerializedName("data") val channelDetail: ChannelDetail,
   @SerialName("message") val message: String
) : java.io.Serializable
