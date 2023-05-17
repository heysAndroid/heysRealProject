package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.model.network.ContentDetail
import com.example.heysrealprojcet.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ContestExtracurricularDetailViewModel @Inject constructor(
   private val contentRepository: ContentRepository
) : ViewModel() {
   private val _title = MutableLiveData<String>()
   val title: LiveData<String> = _title

   private val _company = MutableLiveData<String>()
   val company: LiveData<String> = _company

   private val _target = MutableLiveData<String>()
   val target: LiveData<String> = _target

   private val _benefit = MutableLiveData<String>()
   val benefit: LiveData<String> = _benefit

   private val _contentText = MutableLiveData<String>()
   val contentText: LiveData<String> = _contentText

   private val _contact = MutableLiveData<String>()
   val contact: LiveData<String> = _contact

   private val _startDate = MutableLiveData<String>()
   val startDate: LiveData<String> = _startDate

   private val _endDate = MutableLiveData<String>()
   val endDate: LiveData<String> = _endDate

   private val _viewCount = MutableLiveData<Int>()
   val viewCount: LiveData<Int> = _viewCount

   private val _channelCount = MutableLiveData<Int>()
   val channelCount: LiveData<Int> = _channelCount

   private val _linkUrl = MutableLiveData<String>()
   val linkUrl: LiveData<String> = _linkUrl

   private val _thumbnailUri = MutableLiveData<String>()
   val thumbnailUri: LiveData<String> = _thumbnailUri

   private val _interests = MutableLiveData<ArrayList<String>>()
   val interests: LiveData<ArrayList<String>> = _interests

   private val _interestString by lazy { MutableLiveData("") }
   val interestString: LiveData<String> = _interestString

   private val _isBookMarked = MutableLiveData(false)
   val isBookMarked: LiveData<Boolean> = _isBookMarked

   private val _dday = MutableLiveData<Int>()
   val dday: LiveData<Int> = _dday
   fun getContentDetail(token: String, id: Int) = contentRepository.getContentDetail(token, id).asLiveData()
   fun contentViewCountUp(token: String, id: Int) = contentRepository.contentViewCountUp(token, id).asLiveData()

   fun contentAddBookmark(token: String, id: Int) = contentRepository.contentAddBookmark(token, id).asLiveData()

   fun contentRemoveBookmark(token: String, id: Int) = contentRepository.contentRemoveBookmark(token, id).asLiveData()
   fun receiveContentDetail(contentDetail: ContentDetail?) {
      contentDetail?.let {
         _title.value = it.title
         _company.value = it.company
         _target.value = it.target
         _benefit.value = it.benefit
         _contentText.value = it.contentText
         _contact.value = it.contact
         _startDate.value = it.startDate
         _endDate.value = it.endDate
         _viewCount.value = it.viewCount
         _channelCount.value = it.channelCount
         _linkUrl.value = it.linkUrl
         _thumbnailUri.value = it.thumbnailUri
         _interests.value = it.interests
         _isBookMarked.value = it.isBookMarked
         _dday.value = it.dday
      }

      setStartDate()
      setEndDate()
      setInterestString()
   }

   private fun setStartDate() {
      var datetime = LocalDateTime.parse(endDate.value)
      var year = datetime.year.minus(2000)
      var month = datetime.monthValue
      var day = datetime.dayOfMonth
      _startDate.value = "${year}년 ${month}월 ${day}일"
   }

   private fun setEndDate() {
      var datetime = LocalDateTime.parse(endDate.value)
      var year = datetime.year.minus(2000)
      var month = datetime.monthValue
      var day = datetime.dayOfMonth
      var hour = datetime.hour
      var min = datetime.minute
      _endDate.value = "${year}년 ${month}월 ${day}일 ${hour}시 ${min}분"
   }

   private fun setInterestString() {
      interests.value?.forEach { _interestString.value += "#${it} " }
      _interestString.value = _interestString.value?.dropLast(1)
   }
}