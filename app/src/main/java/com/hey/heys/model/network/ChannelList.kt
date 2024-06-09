package com.hey.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelList(
   @SerializedName("id") val id: Int,
   @SerialName("type") val type: String,
   @SerialName("name") val name: String,
   @SerialName("viewCount") val viewCount: Int,
   @SerialName("joinRemainCount") val joinRemainCount: Int,
   @SerialName("capacityCount") val capacityCount:Int,
   @SerialName("pastDay") val pastDay: Int,
   @SerialName("thumbnailUri") val thumbnailUri: String,
   @SerialName("dday") val dday: Int
) : java.io.Serializable
