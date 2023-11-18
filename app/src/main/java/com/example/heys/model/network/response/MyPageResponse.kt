package com.example.heys.model.network.response

import com.example.heys.model.network.MyPage
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponse(
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerializedName("data") val user: MyPage,
) : java.io.Serializable
