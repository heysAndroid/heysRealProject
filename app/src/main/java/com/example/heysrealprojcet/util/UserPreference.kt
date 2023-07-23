package com.example.heysrealprojcet.util

import com.chibatching.kotpref.KotprefModel

object UserPreference : KotprefModel() {
   fun init() {
      isAutoLogin = false
      name = ""
      phoneNumber = ""
      age = 0
      birthday = ""
      gender = ""
      phoneNumber = ""
      interests = ""
      accessToken = ""
      deviceToken = ""
      mbti = ""
      job = ""
      skill = ""
      introduce = ""
      percentage = 0
      waitingChannelCount = 0
      joinChannelCount = 0
   }

   /*
   * 자동 로그인 여부
    */
   var isAutoLogin by booleanPref(false)

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
   var age by intPref()

   /*
   * 사용자 생년월일
    */
   var birthday by stringPref()

   /*
   * 사용자 성별
    */
   var gender by stringPref()

   /*
   * 사용자 비밀번호
    */
   var password by stringPref()

   /*
   * 사용자 관심 분야
    */
   var interests by stringPref()

   /*
   * 로그인 / 회원가입 시 accessToken 에 값 저장
    */
   var accessToken by stringPref()

   /*
   * device token
    */
   var deviceToken by stringPref()

   /*
   * mbti
    */
   var mbti by stringPref()

   /*
   * job
    */
   var job by stringPref()

   /*
   * skill
    */
   var skill by stringPref()

   /*
   * 한줄소개
    */
   var introduce by stringPref()

   /*
   * 퍼센트
    */
   var percentage by intPref()

   /*
   * 퍼센트
    */
   var joinChannelCount by intPref()

   /*
   * 퍼센트
    */
   var waitingChannelCount by intPref()
}