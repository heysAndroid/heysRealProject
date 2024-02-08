package com.hey.heys.model.network.response

import com.google.gson.annotations.SerializedName

data class CheckPhoneNumberResponse(
   @SerializedName("result") val isUserExisted: Boolean
) : java.io.Serializable