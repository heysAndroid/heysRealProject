package com.example.heysrealprojcet.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Phone(
   @SerializedName("phone") val phone: String
) : java.io.Serializable
