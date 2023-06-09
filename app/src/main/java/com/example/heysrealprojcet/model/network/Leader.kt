package com.example.heysrealprojcet.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Leader(
   @SerialName("id") val id: Int,
   @SerializedName("username") val name: String,
   @SerialName("introduceText") val introduceText: String,
   @SerialName("percentage") val percentage: Int,
) : java.io.Serializable
