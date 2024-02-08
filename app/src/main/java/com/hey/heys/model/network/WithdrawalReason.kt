package com.hey.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawalReason(
   @SerializedName("reason") val reason: String
) : java.io.Serializable