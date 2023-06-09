package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.network.ChannelDetail
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelDetailResponse(
   @SerializedName("data") val channelDetail: ChannelDetail,
   @SerialName("message") val message: String
) : java.io.Serializable
