package com.example.heysrealprojcet.ui.channel.edit

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.heysrealprojcet.model.network.ChannelDetail
import com.example.heysrealprojcet.repository.ChannelRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChannelEditViewModel @Inject constructor(
   private val channelRepository: ChannelRepository) : BaseViewModel() {
   private val _recruitEndDate = MutableLiveData<String>()
   val recruitEndDate: LiveData<String> = _recruitEndDate

   private val _recruitEndTime = MutableLiveData<String>()
   val recruitEndTime: LiveData<String> = _recruitEndTime

   private val _interestString by lazy { MutableLiveData("") }
   val interestString: LiveData<String> = _interestString

   private val _purposeString = MutableLiveData("")
   val purposeString: LiveData<String> = _purposeString

   private val _channelDetail = MutableLiveData<ChannelDetail>()
   val channelDetail: LiveData<ChannelDetail> = _channelDetail

   val title = MutableLiveData<String>()
   val contentText = MutableLiveData<String>()
   val recruitText = MutableLiveData<String>()

   val link1 = MutableLiveData(channelDetail.value?.links?.get(0)?.link ?: "")
   val link2 = MutableLiveData(channelDetail.value?.links?.get(1)?.link ?: "")
   val linkCount = MutableLiveData(0)

   private val _channelTitleLetterCount = MutableLiveData<Int>()
   val channelTitleLetterCount: LiveData<Int> = _channelTitleLetterCount

   private val _channelContentLetterCount = MutableLiveData<Int>()
   val channelContentLetterCount: LiveData<Int> = _channelContentLetterCount

   private val _channelRecruitLetterCount = MutableLiveData<Int>()
   val channelRecruitLetterCount: LiveData<Int> = _channelRecruitLetterCount

   private val _purposeArray = MutableLiveData<List<String>>()
   val purposeArray: LiveData<List<String>> = _purposeArray

   fun setTitle(value: String) {
      title.value = value
   }

   fun setContentText(value: String) {
      contentText.value = value
   }

   fun setRecruitText(value: String) {
      recruitText.value = value
   }

   fun setRecruitEndDate(date: String) {
      _recruitEndDate.value = date
   }

   fun setRecruitEndTime(time: String) {
      _recruitEndTime.value = time
   }

   fun setInterestString(interest: String) {
      _interestString.value = interest
   }

   fun setPurposeString(purpose: String) {
      _purposeString.value = purpose
   }

   fun setPurposeArray(purpose: List<String>) {
      _purposeArray.value = purpose
   }

   fun setChannelDetail(channelDetail: ChannelDetail) {
      _channelDetail.value = channelDetail
   }

   fun setContentTextCount(contentLetterCount: Int) {
      _channelContentLetterCount.value = contentLetterCount
   }

   fun setRecruitTextCount(channelRecruitLetterCount: Int) {
      _channelRecruitLetterCount.value = channelRecruitLetterCount
   }

   val channelTitleTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _channelTitleLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {}
   }

   val channelContentTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _channelContentLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {}
   }

   val channelRecruitTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _channelRecruitLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {}
   }
}