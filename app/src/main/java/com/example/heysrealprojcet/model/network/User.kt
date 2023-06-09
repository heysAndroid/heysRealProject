package com.example.heysrealprojcet.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
   @SerializedName("phone") val phone: String,
   @SerializedName("username") val name: String,
   @SerializedName("password") val password: String,
   @SerializedName("birthDate") val birthDate: String,
   @SerializedName("gender") val gender: String,
   @SerializedName("interests") val interests: ArrayList<String>
) : java.io.Serializable
