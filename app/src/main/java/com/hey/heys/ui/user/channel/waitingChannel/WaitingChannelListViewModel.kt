package com.hey.heys.ui.user.channel.waitingChannel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.hey.heys.model.network.MyChannel
import com.hey.heys.repository.ChannelRepository
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WaitingChannelListViewModel @Inject constructor(private val channelRepository: ChannelRepository, private val userRepository: MyPageRepository) : BaseViewModel() {
   private val _waitingChannelList = MutableLiveData<List<MyChannel>>()
   val waitingChannelList: LiveData<List<MyChannel>> = _waitingChannelList

   fun setWaitingChannel(value: List<MyChannel>) {
      _waitingChannelList.value = value
   }

   fun getMyChannel(token: String, status: String) =
      channelRepository.getMyChannelByStatus(token, status).asLiveData()
}