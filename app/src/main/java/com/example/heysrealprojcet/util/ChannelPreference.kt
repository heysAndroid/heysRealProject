package com.example.heysrealprojcet.util

import com.chibatching.kotpref.KotprefModel

object ChannelPreference : KotprefModel() {
   fun reset() {
      channelPurpose = ""
      channelForm = ""
      channelRegion = ""
      channelCapacity = 0
      channelRecruitmentMethod = ""
      channelRecruitPeriod = ""
   }

   /*
   채널목적
    */
   var channelPurpose by stringPref()

   /*
   활동 형태
    */
   var channelForm by stringPref()

   /*
   활동 지역
    */
   var channelRegion by stringPref()

   /*
   참여 인원
    */
   var channelCapacity by intPref()

   /*
   모집 방식
    */
   var channelRecruitmentMethod by stringPref()

   /*
  모집 기간
   */
   var channelRecruitPeriod by stringPref()
}