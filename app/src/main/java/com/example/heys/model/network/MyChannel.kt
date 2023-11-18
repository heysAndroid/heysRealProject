package com.example.heys.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyChannel(
   @SerialName("id") val id: Int,
   @SerialName("type") val type: String,
   @SerialName("name") val name: String,
   @SerialName("leaderName") val leaderName: String,
   @SerialName("thumbnailUri") val thumbnailUri: String,
   @SerialName("isLeader") val isLeader: Boolean,
   @SerialName("isClosed") val isClosed: Boolean,
   @SerialName("dday") val dday: Int,
) : java.io.Serializable