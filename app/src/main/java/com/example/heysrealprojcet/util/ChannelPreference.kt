package com.example.heysrealprojcet.util

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref
import com.example.heysrealprojcet.enums.ChannelType

object ChannelPreference : KotprefModel() {
   fun reset() {
      channelForm = ""
      channelType = ChannelType.Extra.typeEng
      channelRegion = ""
      channelCapacity = 0
      channelRecruitmentMethod = ""
      channelRecruitEndDay = ""
      channelRecruitEndTime = ""
      channelActivity = ""
      channelMember = ""
   }

   /*
  채널명
   */
   var channelName by stringPref()

   var channelType by stringPref()

   /*
   채널목적 배열
    */
   var channelPurposeArray by gsonPref(arrayListOf<String>())

   /*
   활동 형태
    */
   var channelForm by stringPref()

   /*
  활동 형태 영어
   */
   var channelFormEng by stringPref()

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
   모집 방식 영어
    */
   var channelRecruitmentMethodEng by stringPref()

   /*
  모집 마감 날짜
   */
   var channelRecruitEndDay by stringPref()

   /*
  모집 마감 시간
   */
   var channelRecruitEndTime by stringPref()

   /*
   * 모집 마감 일시
    */
   var channelRecruitEndDateTime by stringPref()

   /*
  관심 분야
   */
   var channelInterestArray by gsonPref(arrayListOf<String>())

   /*
   채널 활동
    */
   var channelActivity by stringPref()

   /*
   채널 멤버
    */
   var channelMember by stringPref()

   /*
   링크 1
    */
   var link1 by stringPref()

   /*
   링크 2
    */
   var link2 by stringPref()
}
