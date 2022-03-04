package com.example.heysrealprojcet.util

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref

object UserPreference : KotprefModel() {
   /*
   * 사용자 이름
    */
   var name by stringPref()

   /*
   * 사용자 전화번호
    */
   var phoneNumber by stringPref()

   /*
   * 사용자 나이
    */
   var age by stringPref()

   /*
   * 사용자 성별
    */
   var gender by stringPref()

   /*
   * 사용자 역할 (기본 : user)
    */
   var role by stringPref("user")

   /*
   * 사용자 비밀번호
    */
   var password by stringPref()

   /*
   * 사용자 관심 분야 (api 대로 일단 만들어놓음)
    */
   var preference by intPref(1)

   /*
   * ? 모르겠음
    */
   var categoryId by intPref(2)
}