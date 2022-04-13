package com.example.heysrealprojcet.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
   @SerializedName("name") val name: String,
   @SerializedName("phone") val phone: String,
   @SerializedName("age") val age: String,
   @SerializedName("gender") val gender: String,
   @SerializedName("role") val role: String = "user",
   @SerializedName("password") val encryptedPassword: String,
   @SerializedName("userCategories") val userCategories: List<UserCategory>
) : java.io.Serializable
