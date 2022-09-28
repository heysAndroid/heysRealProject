package com.example.heysrealprojcet.model

data class UserProfile(
   val name: String,
   val interest: List<String>,
   val job: String,
   val skill: List<String>,
   val introduction: String = "한 줄 소개가 보여집니다.",
   val imageResId: Int
)
