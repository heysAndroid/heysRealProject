package com.example.heys.model

import com.example.heys.enums.ChannelStatus
import com.example.heys.enums.ChannelType

data class Channel(
   val resId: Int,
   val title: String,
   val period: Int,
   val status: ChannelStatus,
   val capacity: Int,
   val view: Int,
   val type: ChannelType = ChannelType.Contest
)