package com.example.heys.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentDetail(
   @SerialName("title") val title: String,
   @SerialName("company") val company: String,
   @SerialName("target") val target: String,
   @SerialName("benefit") val benefit: String,
   @SerialName("contentText") val contentText: String,
   @SerialName("contact") val contact: String,
   @SerialName("startDate") val startDate: String,
   @SerialName("endDate") val endDate: String,
   @SerialName("viewCount") val viewCount: Int,
   @SerialName("channelCount") val channelCount: Int,
   @SerialName("linkUrl") val linkUrl: String,
   @SerialName("thumbnailUri") val thumbnailUri: String,
   @SerialName("interests") val interests: ArrayList<String>,
   @SerialName("isBookMarked") val isBookMarked: Boolean,
   @SerialName("dday") val dday: Int,
) : java.io.Serializable