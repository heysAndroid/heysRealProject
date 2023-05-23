package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.network.ChannelList
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyListResponse(
   @SerializedName("data") val data: ArrayList<ChannelList>,
   @SerialName("totalPage") val totlaPage: Int,
   @SerialName("totalPage") val totlaPage: Int,
) : java.io.Serializable