package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.network.ContentDetail
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailResponse(
   @SerializedName("data") val contentDetail: ContentDetail,
   @SerialName("message") val message: String
) : java.io.Serializable