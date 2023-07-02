package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.OtherUser
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
   @SerialName("message") val message: String,
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerializedName("data") val user: OtherUser,
) : java.io.Serializable