package com.example.heysrealprojcet.model

import com.example.heysrealprojcet.enums.Gender
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPage(
   @SerializedName("userName") val name: String,
   @SerialName("phone") val phone: String,
   @SerialName("gender") val gender: Gender,
   @SerialName("age") val age: Int,
   @SerialName("job") val job: String,
   @SerialName("profileUrl") val profileUrl: String,
   @SerialName("introduce") val introduce: String,
   @SerialName("capability") val capability: String,
   @SerialName("interests") val interests: Array<String>,
   @SerialName("joinChannelCount") val joinChannelCount: Int,
   @SerialName("waitingChannelCount") val waitingChannelCount: Int,
) : java.io.Serializable