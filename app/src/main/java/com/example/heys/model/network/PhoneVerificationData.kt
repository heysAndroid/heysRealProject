package com.example.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneVerificationData(
   @SerializedName("isVerification") val isVerification: Boolean,
) : java.io.Serializable