package com.hey.heys.model.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WaitingUserList(
   @SerialName("id") val id: Int,
   @SerialName("percentage") val percentage: Int,
   @SerialName("gender") val gender: String,
   @SerialName("date") val date: String,
) : java.io.Serializable, Parcelable