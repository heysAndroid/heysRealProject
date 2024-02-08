package com.hey.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageEdit(
   @SerialName("phone") val phone: String,
   @SerialName("gender") val gender: String,
   @SerializedName("userName") val name: String?,
   @SerialName("job") val job: String?,
   @SerialName("capability") val capability: String?,
   @SerialName("introduce") val introduce: String?,
   @SerialName("userPersonality") val userPersonality: String?,
   @SerialName("interests") val interests: Array<String>,
   @SerialName("profileLinks") val profileLinks: Array<String>,
) : java.io.Serializable