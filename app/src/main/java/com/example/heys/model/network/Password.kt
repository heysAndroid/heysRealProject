package com.example.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Password(
   @SerializedName("password") val password: String
) : java.io.Serializable
