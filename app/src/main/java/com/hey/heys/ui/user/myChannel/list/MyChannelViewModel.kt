package com.hey.heys.ui.user.myChannel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.hey.heys.model.network.MyChannel
import com.hey.heys.repository.ChannelRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyChannelViewModel @Inject constructor(
   private val channelRepository: ChannelRepository) : BaseViewModel() {
   private val _myChannelList = MutableLiveData<List<MyChannel>>()
   val myChannelList: LiveData<List<MyChannel>> = _myChannelList

   fun setMyChannelList(list: List<MyChannel>?) {
      _myChannelList.value = list ?: listOf()
   }

   fun getMyChannelList(token: String) = channelRepository.getMyChannelList(token).asLiveData()
}