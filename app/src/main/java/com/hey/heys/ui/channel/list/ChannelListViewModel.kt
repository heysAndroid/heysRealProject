package com.hey.heys.ui.channel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hey.heys.model.network.ChannelList
import com.hey.heys.repository.ChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ChannelListViewModel @Inject constructor(
   private val repository: ChannelRepository
) : ViewModel() {

   fun getChannel(
      token: String,
      id: Int,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      purposes: ArrayList<String>?,
      online: String?,
      location: String?,
      includeClosed: Boolean? = true, ): Flow<PagingData<ChannelList>> = repository.getChannelList(token, id, interest, lastRecruitDate, purposes, online, location, includeClosed).cachedIn(viewModelScope)
}