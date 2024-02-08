package com.hey.heys.model

import com.hey.heys.enums.ContentOrder

data class ExtracurricularType(
   val description: String,
   val type: String,
   val resId: Int,
   val order: String = ContentOrder.Default.order
)