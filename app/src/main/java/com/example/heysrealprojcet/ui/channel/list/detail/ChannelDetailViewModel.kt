package com.example.heysrealprojcet.ui.channel.list.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.model.*
import com.example.heysrealprojcet.repository.ChannelRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChannelDetailViewModel @Inject constructor(
   private val channelRepository: ChannelRepository) : BaseViewModel() {
   private val _thumbnailUri = MutableLiveData<String>()
   val thumbnailUri: LiveData<String> = _thumbnailUri

   private val _title = MutableLiveData<String>()
   val title: LiveData<String> = _title

   private val _channelForm = MutableLiveData<String>()
   val channelForm: LiveData<String> = _channelForm

   private val _location = MutableLiveData<String>()
   val location: LiveData<String> = _location

   private val _capacity = MutableLiveData<Int>()
   val capacity: LiveData<Int> = _capacity

   private val _recruitMethod = MutableLiveData<String>()
   val recruitMethod: LiveData<String> = _recruitMethod

   private val _recruitEndDateTime = MutableLiveData<String>()
   val recruitEndDateTime: LiveData<String> = _recruitEndDateTime

   private val _interests = MutableLiveData<ArrayList<String>>()
   val interests: LiveData<ArrayList<String>> = _interests

   private val _contentText = MutableLiveData<String>()
   val contentText: LiveData<String> = _contentText

   private val _recruitText = MutableLiveData<String>()
   val recruitText: LiveData<String> = _recruitText

   private val _leader = MutableLiveData<Leader>()
   val leader: LiveData<Leader> = _leader

   private val _channelPurposes = MutableLiveData<ArrayList<ChannelPurpose>>()
   val channelPurposes: LiveData<ArrayList<ChannelPurpose>> = _channelPurposes

   private val _links = MutableLiveData<ArrayList<ChannelLink>>()
   val links: LiveData<ArrayList<ChannelLink>> = _links

   private val _contentData = MutableLiveData<ContentData>()
   val contentData: LiveData<ContentData> = _contentData

   private val _relationshipWithMe = MutableLiveData<String>()
   val relationshipWithMe: LiveData<String> = _relationshipWithMe

   private val _isBookMarked = MutableLiveData<Boolean>()
   val isBookMarked: LiveData<Boolean> = _isBookMarked

   private val _approvedUserList = MutableLiveData<ArrayList<ApprovedUserList>>()
   val approvedUserList: LiveData<ArrayList<ApprovedUserList>> = _approvedUserList

   private val _waitingUserList = MutableLiveData<ArrayList<WaitingUserList>>()
   val waitingUserList: LiveData<ArrayList<WaitingUserList>> = _waitingUserList

   private val _recruitEndDate = MutableLiveData<String>()
   val recruitEndDate: LiveData<String> = _recruitEndDate

   private val _recruitEndTime = MutableLiveData<String>()
   val recruitEndTime: LiveData<String> = _recruitEndTime

   private val _channelInterest = MutableLiveData<String>()
   val channelInterest: LiveData<String> = _channelInterest

   fun getChannelDetail(token: String, id: Int) = channelRepository.getChannelDetail(token, id).asLiveData()

   fun receiveChannelDetail(channelDetail: ChannelDetail?) {
      channelDetail?.let {
         _thumbnailUri.value = it.thumbnailUri
         _title.value = it.title
         _channelForm.value = it.online
         _location.value = it.location
         _capacity.value = it.limit
         _recruitMethod.value = it.recruitMethod
         _recruitEndDate.value = it.lastRecruitDate
         _interests.value = it.interests
         _contentText.value = it.contentText
         _leader.value = it.leader
         _channelPurposes.value = it.purposes
         _links.value = it.links
         _contentData.value = it.contentData
         _relationshipWithMe.value = it.relationshipWithMe
         _isBookMarked.value = it.isBookMarked
         _approvedUserList.value = it.approvedUserList
         _waitingUserList.value = it.waitingUserList
      }
   }
}