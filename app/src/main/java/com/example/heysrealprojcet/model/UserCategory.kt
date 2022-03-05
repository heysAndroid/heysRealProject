package com.example.heysrealprojcet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCategory(
  @SerialName("preference") val preference: Int,
  @SerialName("categoryId") val categoryId: Int
) : java.io.Serializable