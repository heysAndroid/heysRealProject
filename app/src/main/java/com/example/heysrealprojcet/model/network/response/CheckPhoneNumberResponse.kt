package com.example.heysrealprojcet.model.network.response

import com.google.gson.annotations.SerializedName

data class CheckPhoneNumberResponse(
//   @SerialName("isUserExisted") val isUserExisted: Boolean
   @SerializedName("result") val isUserExisted: Boolean
) : java.io.Serializable