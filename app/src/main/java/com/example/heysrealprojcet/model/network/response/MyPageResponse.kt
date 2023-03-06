package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.MyPage
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponse(
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerializedName("data") val user: MyPage,
) : java.io.Serializable
