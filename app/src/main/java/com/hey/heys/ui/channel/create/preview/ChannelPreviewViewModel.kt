package com.hey.heys.ui.channel.create.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hey.heys.model.network.Study
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.response.CreateStudyResponse
import com.hey.heys.model.network.response.MyPageResponse
import com.hey.heys.repository.ChannelRepository
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.repository.StudyRepository
import com.hey.heys.ui.base.BaseViewModel
import com.hey.heys.util.ChannelPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ChannelPreviewViewModel @Inject constructor(
   private val studyRepository: StudyRepository, private val channelRepository: ChannelRepository, private val myPageRepository: MyPageRepository) : BaseViewModel() {
   var channelName = MutableLiveData(ChannelPreference.channelName)
   var channelPurposeString = MutableLiveData("")
   var channelCapacity = MutableLiveData(ChannelPreference.channelCapacity.toString())
   var channelForm = MutableLiveData(ChannelPreference.channelForm)
   var channelRegion = MutableLiveData(ChannelPreference.channelRegion)
   var channelRecruitmentMethod = MutableLiveData(ChannelPreference.channelRecruitmentMethod)
   var channelRecruitEndDay = MutableLiveData(ChannelPreference.channelRecruitEndDay)
   var channelRecruitEndTime = MutableLiveData(ChannelPreference.channelRecruitEndTime)
   var channelInterest = MutableLiveData("")
   var channelActivity = MutableLiveData(ChannelPreference.channelActivity)
   var channelMember = MutableLiveData(ChannelPreference.channelMember)
   var link1 = MutableLiveData(ChannelPreference.link1)
   var link2 = MutableLiveData(ChannelPreference.link2)

   var channelRecruitEndDayString = MutableLiveData<String>()
   var channelRecruitEndTimeString = MutableLiveData<String>()

   private val _responseCreateStudy: MutableLiveData<NetworkResult<CreateStudyResponse>> = MutableLiveData()
   val responseCreateStudy: LiveData<NetworkResult<CreateStudyResponse>> = _responseCreateStudy

   private val _responseMyPage: MutableLiveData<NetworkResult<MyPageResponse>> = MutableLiveData()
   val responseMyPage: LiveData<NetworkResult<MyPageResponse>> = _responseMyPage

   init {
      setChannelPurposeString()
      setChannelInterestString()
      setEndDateString()
      setEndTimeString()
   }

   private fun setChannelPurposeString() {
      when (ChannelPreference.channelPurposeArray.size) {
         1 -> channelPurposeString.value = ChannelPreference.channelPurposeArray.first()
         else -> {
            ChannelPreference.channelPurposeArray.forEach { channelPurposeString.value += "$it, " }
            channelPurposeString.value = channelPurposeString.value?.dropLast(2)
         }
      }
   }

   private fun setChannelInterestString() {
      when (ChannelPreference.channelInterestArray.size) {
         1 -> channelInterest.value = ChannelPreference.channelInterestArray.first()
         else -> {
            ChannelPreference.channelInterestArray.forEach { channelInterest.value += "$it, " }
            channelInterest.value = channelInterest.value?.dropLast(2)
         }
      }
   }

   private fun setEndDateString() {
      var dateParse = LocalDate.parse(channelRecruitEndDay.value)
      var year = dateParse.year.minus(2000)
      var month = dateParse.monthValue
      var day = dateParse.dayOfMonth
      channelRecruitEndDayString.value = "${year}년 ${month}월 ${day}일"
   }

   private fun setEndTimeString() {
      var timeParse = LocalTime.parse(channelRecruitEndTime.value)
      var hour = timeParse.hour
      var min = timeParse.minute
      channelRecruitEndTimeString.value = "${hour}시 ${min}분"
   }

   fun createStudy(token: String, study: Study) = viewModelScope.launch {
      studyRepository.createStudy(token, study).collect { values ->
         _responseCreateStudy.value = values
      }
   }

   fun createContentChannel(token: String, contentId: Int, channel: Study) = channelRepository.createContentChannel(token, contentId, channel).asLiveData()

   fun getMyInfo(token: String) = viewModelScope.launch {
      myPageRepository.getMyInfo(token).collect { values ->
         _responseMyPage.value = values
      }
   }
}