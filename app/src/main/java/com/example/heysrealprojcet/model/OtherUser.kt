package com.example.heysrealprojcet.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OtherUser(
   @SerializedName("userName") val userName: String,
   @SerializedName("gender") val gender: String,
   @SerializedName("job") val job: String,
   @SerializedName("introduce") val introduce: String,
   @SerializedName("capability") val capability: String,
   @SerializedName("userPersonality") val userPersonality: String,
   @SerializedName("interests") val interests: ArrayList<String>,
   @SerializedName("profileLinks") val profileLinks: ArrayList<String>,
   @SerializedName("percentage") val percentage: Int
) : java.io.Serializable