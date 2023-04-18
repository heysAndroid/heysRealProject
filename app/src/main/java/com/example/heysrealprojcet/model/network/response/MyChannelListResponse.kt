package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.MyChannel
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MyChannelListResponse(
   @SerialName("data") val myChannel: MyChannel,
   @SerialName("message") val message: String
) : java.io.Serializable