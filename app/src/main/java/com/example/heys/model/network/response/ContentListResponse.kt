package com.example.heys.model.network.response

import com.example.heys.model.network.Content
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentListResponse(
   @SerialName("data") val data: ArrayList<Content>,
   @SerialName("totalPage") val totalPage: Int,
   @SerialName("message") val message: String
) : java.io.Serializable