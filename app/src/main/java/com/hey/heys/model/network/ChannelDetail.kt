package com.hey.heys.model.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ChannelDetail(
   @SerialName("id") val id: Int,
   @SerialName("thumbnailUri") val thumbnailUri: String,
   @SerialName("title") val title: String,
   @SerialName("online") val online: String,
   @SerialName("location") val location: String,
   @SerializedName("limitPeople") val limit: Int,
   @SerialName("recruitMethod") val recruitMethod: String,
   @SerialName("lastRecruitDate") val lastRecruitDate: String,
   @SerialName("interests") val interests: ArrayList<String>,
   @SerialName("contentText") val contentText: String,
   @SerialName("recruitText") val recruitText: String,
   @SerialName("joinRemainCount") val joinRemainCount: Int,
   @SerialName("approvedCount") val approvedCount:Int,
   @SerialName("pastDay") val pastDay: Int,
   @SerialName("leader") val leader: Leader,
   @SerialName("purposes") val purposes: ArrayList<ChannelPurpose>,
   @SerialName("links") val links: ArrayList<ChannelLink>,
   @SerialName("contentData") val contentData: ContentData?,
   @SerialName("relationshipWithMe") val relationshipWithMe: String,
   @SerialName("isBookMarked") val isBookMarked: Boolean,
   @SerialName("approvedUserList") val approvedUserList: ArrayList<ApprovedUserList>,
   @SerialName("waitingUserList") val waitingUserList: ArrayList<WaitingUserList>,
) : java.io.Serializable, Parcelable