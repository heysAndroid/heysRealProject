package com.example.heys.model

import com.example.heys.enums.ContentOrder

data class ExtracurricularType(
   val description: String,
   val type: String,
   val resId: Int,
   val order: String = ContentOrder.Default.order
)