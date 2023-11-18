package com.example.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPage(
   @SerializedName("userName") val name: String,
   @SerialName("phone") val phone: String,
   @SerialName("gender") val gender: String,
   @SerialName("birthDate") val birthDate: String,
   @SerialName("job") val job: String,
   @SerialName("profileLinks") val profileLinks: Array<String>,
   @SerialName("introduce") val introduce: String,
   @SerialName("capability") val capability: String,
   @SerialName("userPersonality") val userPersonality: String,
   @SerialName("interests") val interests: Array<String>,
   @SerialName("percentage") val percentage: Int,
   @SerialName("joinChannelCount") val joinChannelCount: Int,
   @SerialName("waitingChannelCount") val waitingChannelCount: Int,
) : java.io.Serializable