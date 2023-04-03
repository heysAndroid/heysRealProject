package com.example.heysrealprojcet.model.network.response

import com.example.heysrealprojcet.model.network.StudyList
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class StudyListResponse(
   @SerializedName("data") val data: ArrayList<StudyList>,
) : java.io.Serializable