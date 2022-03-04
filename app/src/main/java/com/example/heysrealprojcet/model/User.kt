package com.example.heysrealprojcet.model

import com.example.heysrealprojcet.enums.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
   @SerialName("name") val name: String,
   @SerialName("phone") val phoneNumber: String,
   @SerialName("age") val age: String,
   @SerialName("gender") val gender: Gender,
   @SerialName("role") val role: String = "user",
   @SerialName("password") val password: String,
   @SerialName("userCategories") val userCategory: UserCategory
) : java.io.Serializable
