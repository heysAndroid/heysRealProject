package com.example.heysrealprojcet.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Content(
   @SerialName("id") val id: Int,
   @SerialName("title") val title: String,
   @SerialName("company") val company: String,
   @SerialName("viewCount") val viewCount: Int,
   @SerialName("channelCount") val channelCount: Int,
   @SerialName("previewImgUri") val previewImgUri: String,
   @SerialName("dday") val dday: Int
) : java.io.Serializable
