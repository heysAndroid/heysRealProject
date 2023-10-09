package com.example.heysrealprojcet.model

import com.example.heysrealprojcet.enums.ContentOrder

data class ExtracurricularType(
   val description: String,
   val type: String,
   val resId: Int,
   val order: String = ContentOrder.Default.order
)