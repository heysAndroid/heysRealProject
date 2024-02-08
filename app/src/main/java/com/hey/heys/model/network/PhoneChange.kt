package com.hey.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneChange(
   @SerializedName("phoneNumber") val phoneNumber: String,
   @SerializedName("password") val password: String
) : java.io.Serializable