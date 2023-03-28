package com.example.heysrealprojcet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WaitingUserList(
   @SerialName("id") val id: Int,
   @SerialName("percentage") val percentage: Int,
   @SerialName("gender") val gender: String
) : java.io.Serializable
