package com.hey.heys.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentData(
   @SerialName("id") val id: Int,
   @SerialName("title") val title: String,
   @SerialName("previewImgUrl") val previewImgUrl: String,
   @SerialName("company") val company: String,
   @SerialName("type") val type: String,
) : java.io.Serializable