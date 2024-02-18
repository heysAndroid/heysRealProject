package com.hey.heys.model.network.response

import com.google.gson.annotations.SerializedName
import com.hey.heys.model.OtherUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
   @SerialName("message") val message: String,
   @SerialName("isSuccess") val isSuccess: Boolean,
   @SerializedName("data") val user: OtherUser,
) : java.io.Serializable