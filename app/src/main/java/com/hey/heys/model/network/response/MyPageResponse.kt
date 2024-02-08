package com.hey.heys.model.network.response

import com.google.gson.annotations.SerializedName
import com.hey.heys.model.network.MyPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponse(
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerializedName("data") val user: MyPage,
) : java.io.Serializable