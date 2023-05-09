package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.ChannelDetail
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
   @SerialName("") val message: String
) : java.io.Serializable
