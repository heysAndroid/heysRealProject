package com.example.heys.ui.channel.list.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heys.model.network.ChannelDetail
import com.example.heys.repository.ChannelRepository
import com.example.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChannelDetailViewModel @Inject constructor(
   private val channelRepository: ChannelRepository) : BaseViewModel() {

   private val _recruitEndDate = MutableLiveData<String>()
   val recruitEndDate: LiveData<String> = _recruitEndDate

   private val _recruitEndTime = MutableLiveData<String>()
   val recruitEndTime: LiveData<String> = _recruitEndTime

   private val _interestString by lazy { MutableLiveData("") }
   val interestString: LiveData<String> = _interestString

   private val _purposeString = MutableLiveData("")
   val purposeString: LiveData<String> = _purposeString

   private val _waitingUserNum = MutableLiveData<Int>()
   val waitingUserNum: LiveData<Int> = _waitingUserNum

   private val _channelDetail = MutableLiveData<ChannelDetail>()
   val channelDetail: LiveData<ChannelDetail> = _channelDetail

   fun getChannelDetail(token: String, id: Int) = channelRepository.getChannelDetail(token, id).asLiveData()

   fun channelViewCountUp(token: String, id: Int) = channelRepository.channelViewCountUp(token, id).asLiveData()

   fun channelAddBookmark(token: String, id: Int) = channelRepository.channelAddBookmark(token, id).asLiveData()

   fun channelRemoveBookmark(token: String, id: Int) = channelRepository.channelRemoveBookmark(token, id).asLiveData()

   fun joinChannel(token: String, id: Int) = channelRepository.joinChannel(token, id).asLiveData()

   fun receiveChannelDetail(channelDetail: ChannelDetail?) {
      channelDetail?.let {
         _channelDetail.value = it
         _waitingUserNum.value = it.waitingUserList.size
      }
      setEndDateTimeString()
      setPurposeString()
      setInterestString()
   }

   private fun setEndDateTimeString() {
      var datetime = LocalDateTime.parse(channelDetail.value?.lastRecruitDate)
      var year = datetime.year.minus(2000)
      var month = datetime.monthValue
      var day = datetime.dayOfMonth
      var hour = datetime.hour
      var min = datetime.minute

      _recruitEndDate.value = "${year}년 ${month}월 ${day}일"
      _recruitEndTime.value = "${hour}시 ${min}분"
   }

   private fun setPurposeString() {
      _purposeString.value = ""
      channelDetail.value?.purposes?.forEach { _purposeString.value += "${it.purpose}, " }
      _purposeString.value = _purposeString.value?.dropLast(2)
   }

   private fun setInterestString() {
      _interestString.value = ""
      channelDetail.value?.interests?.forEach { _interestString.value += "${it}, " }
      _interestString.value = _interestString.value?.dropLast(2)
   }
}