package com.hey.heys.ui.user.myChannel.allChannel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.hey.heys.model.network.ChannelList
import com.hey.heys.repository.ChannelRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AllChannelViewModel @Inject constructor(
   private val repository: ChannelRepository
) : BaseViewModel() {
   private val _channelList = MutableLiveData<List<ChannelList>>()
   val channelList: LiveData<List<ChannelList>> = _channelList

   val isChecked = MutableStateFlow(false)

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