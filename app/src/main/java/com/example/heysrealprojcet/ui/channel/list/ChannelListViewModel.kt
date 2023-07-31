package com.example.heysrealprojcet.ui.channel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.model.network.ChannelList
import com.example.heysrealprojcet.repository.ChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChannelListViewModel @Inject constructor(
   private val repository: ChannelRepository
) : ViewModel() {
   private val _channelList = MutableLiveData<List<ChannelList>>()
   val channelList: LiveData<List<ChannelList>> = _channelList

   fun setContentChannelList(list: List<ChannelList>?) {
      _channelList.value = list ?: listOf()
   }

   fun getContentChannel(
      token: String,
      id: Int,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      purposes: ArrayList<String>?,
      online: String?,
      location: String?,
      includeClosed: Boolean? = true,
      page: Int? = 1,
      limit: Int? = 30) = repository.getContentChannelList(token, id, interest, lastRecruitDate, purposes, online, location, includeClosed, page, limit).asLiveData()
}