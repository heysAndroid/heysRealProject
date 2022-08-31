package com.example.heysrealprojcet.model

import com.example.heysrealprojcet.enums.ChannelStatus

data class Channel(
   val resId: Int,
   val title: String,
   val period: Int,
   val status: ChannelStatus,
   val capacity: Int,
   val view: Int,
)