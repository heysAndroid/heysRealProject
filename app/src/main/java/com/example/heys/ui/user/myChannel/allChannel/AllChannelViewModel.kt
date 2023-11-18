package com.example.heys.ui.user.myChannel.allChannel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.heys.model.network.ChannelList
import com.example.heys.repository.ChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllChannelViewModel @Inject constructor(private val repository: ChannelRepository) : ViewModel() {
   private val _channelList = MutableLiveData<List<ChannelList>>()
   val channelList: LiveData<List<ChannelList>> = _channelList

   fun setContentChannelList(list: List<ChannelList>?) {
      _channelList.value = list ?: listOf()
   }

   fun getAllChannel(
      token: String,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      purposes: ArrayList<String>?,
      online: String?,
      location: String?,
      includeClosed: Boolean? = true,
      page: Int? = 1,
      limit: Int? = 30) = repository.getAllChannelList(token, interest, lastRecruitDate, purposes, online, location, includeClosed, page, limit).asLiveData()
}