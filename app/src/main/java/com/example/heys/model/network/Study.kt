package com.example.heys.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Study(
   @SerializedName("name") val name: String?,
   @SerialName("purposes") val purposes: ArrayList<String>,
   @SerialName("online") val online: String?,
   @SerialName("location") val location: String?,
   @SerializedName("limitPeople") val limit: Int?,
   @SerializedName("lastRecruitDate") val recruitEndDate: String?,
   @SerialName("recruitMethod") val recruitMethod: String?,
   @SerialName("contentText") val contentText: String?,
   @SerialName("recruitText") val recruitText: String?,
   @SerialName("thumbnailUri") val thumbnailUri: String? = "",
   @SerialName("linkUri") val linkUri: ArrayList<String>?,
   @SerialName("interests") val interests: ArrayList<String>?
) : java.io.Serializable