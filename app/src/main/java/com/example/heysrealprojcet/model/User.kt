package com.example.heysrealprojcet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
  @SerialName("name") val name: String,
  @SerialName("phone") val phone: String,
  @SerialName("age") val age: String,
  @SerialName("gender") val gender: String,
  @SerialName("role") val role: String = "user",
  @SerialName("password") val password: String,
  @SerialName("userCategories") val userCategories: List<UserCategory>
) : java.io.Serializable
