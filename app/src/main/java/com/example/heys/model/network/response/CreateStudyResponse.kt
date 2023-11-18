package com.example.heys.model.network.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class CreateStudyResponse(
   @SerializedName("channelId") val id: Int,
   @SerialName("message") val message: String,
) : java.io.Serializable
