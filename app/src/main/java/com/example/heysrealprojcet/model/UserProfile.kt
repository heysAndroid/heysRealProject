package com.example.heysrealprojcet.model

data class UserProfile(
   val name: String,
   val interest: List<String>,
   val job: String,
   val skill: List<String>,
   val introduction: String = "예시로 넣는 35자 소개글입니다.\n헤이즈에게 나를 소개해보세요!",
   val imageResId: Int
)
