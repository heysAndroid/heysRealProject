package com.example.heysrealprojcet.model.network.response

import kotlinx.serialization.SerialName

data class CreateStudyResponse(
   @SerialName("channelId") val id: Int,
   @SerialName("message") val message: String,
) : java.io.Serializable
