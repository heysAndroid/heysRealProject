package com.hey.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneVerification(
   @SerializedName("code") val code:String,
   @SerializedName("phone") val phone:String,
):java.io.Serializable
