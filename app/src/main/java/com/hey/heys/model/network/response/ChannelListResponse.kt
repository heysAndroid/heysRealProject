package com.hey.heys.model.network.response

import com.hey.heys.model.network.ChannelList
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelListResponse(
   @SerializedName("data") val data: ArrayList<ChannelList>,
   @SerialName("totalPage") val totalPage: Int,
   @SerialName("message") val message: String,
) : java.io.Serializable