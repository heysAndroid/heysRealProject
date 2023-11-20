package com.example.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserEdit(
   @SerializedName("phone") val phone: String,
   @SerializedName("gender") val gender: String,
   @SerializedName("userName") val name: String,
   @SerializedName("job") val job: String,
   @SerializedName("capability") val capability: String,
   @SerializedName("introduce") val introduce: String,
   @SerializedName("userPersonality") val userPersonality: String,
   @SerializedName("interests") val interests: ArrayList<String>,
   @SerializedName("profileLinks") val profileLinks: ArrayList<String>
) : java.io.Serializable