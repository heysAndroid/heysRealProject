package com.example.heys.model.network.response

import com.example.heys.model.network.MyChannel
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MyChannelListResponse(
   @SerializedName("data") val myChannel: ArrayList<MyChannel>,
   @SerialName("message") val message: String,
) : java.io.Serializable