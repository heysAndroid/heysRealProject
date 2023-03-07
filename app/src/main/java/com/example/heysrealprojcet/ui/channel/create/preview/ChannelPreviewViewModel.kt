package com.example.heysrealprojcet.ui.channel.create.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.Study
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.CreateStudyResponse
import com.example.heysrealprojcet.repository.StudyRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import com.example.heysrealprojcet.util.ChannelPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ChannelPreviewViewModel @Inject constructor(
   private val studyRepository: StudyRepository) : BaseViewModel() {
   var channelName = MutableLiveData(ChannelPreference.channelName)
   var channelPurpose = MutableLiveData("")
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

   init {
      setChannelPurposeString()
      setChannelInterestString()
      setEndDateString()
      setEndTimeString()
   }

   private fun setChannelPurposeString() {
      when (ChannelPreference.channelPurposeArray.size) {
         1 -> channelPurpose.value = ChannelPreference.channelPurposeArray.first()
         else -> {
            ChannelPreference.channelPurposeArray.forEach { channelPurpose.value += "$it, " }
            channelPurpose.value = channelPurpose.value?.dropLast(2)
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
}