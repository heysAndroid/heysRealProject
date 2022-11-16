package com.example.heysrealprojcet.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
   @SerializedName("phone") val phone: String,
   @SerializedName("username") val username: String,
   @SerializedName("password") val password: String,
   @SerializedName("age") val age: Int,
   @SerializedName("gender") val gender: String,
   @SerializedName("interests") val interests: ArrayList<String>
) : java.io.Serializable
