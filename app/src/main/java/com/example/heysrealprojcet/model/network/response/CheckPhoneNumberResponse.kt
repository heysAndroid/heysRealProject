package com.example.heysrealprojcet.model.network.response

import kotlinx.serialization.SerialName

data class CheckPhoneNumberResponse(
   @SerialName("isUserExisted") val isUserExisted: Boolean
) : java.io.Serializable